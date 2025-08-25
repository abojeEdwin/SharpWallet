package com.SharpWallet.service;

import com.SharpWallet.config.BeanConfig;
import com.SharpWallet.data.model.Account;
import com.SharpWallet.data.model.Profile;
import com.SharpWallet.data.model.Status;
import com.SharpWallet.data.model.Transaction;
import com.SharpWallet.data.repository.ProfileRepository;
import com.SharpWallet.data.repository.WalletAccountRepository;
import com.SharpWallet.dto.request.*;
import com.SharpWallet.dto.response.*;
import com.SharpWallet.exception.AccountAlreadyExistException;
import com.SharpWallet.exception.AccountDoesNotExistException;
import com.SharpWallet.exception.AuthorizationException;
import com.SharpWallet.exception.InvalidTransaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.SharpWallet.util.ApiUtil.*;

@Service
public class WalletServiceImpl implements WalletService{

    @Autowired
    private ProfileService profileService;
    @Autowired
    private WalletAccountRepository repository;
    @Autowired
    private BeanConfig beanConfig;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    @Qualifier("monnifyPaymentService")
    private PaymentService monnifyPaymentService;
    @Autowired
    @Qualifier("payStackPaymentService")
    private PaymentService payStackPaymentService;
    @Autowired
    private TransactionService transactionService;


    @Override
    public CreateWalletResponse createAccount(CreateAccountRequest request) throws AccountAlreadyExistException {
        Profile profile = profileService.createProfile(modelMapper.map(request, CreateProfileRequest.class));

        Account newAccount = new Account();
        newAccount.setAccountNumber(request.getPhoneNumber());
        newAccount.setAccountName(request.getFirstName() +" "+ request.getLastName());
        newAccount.setPin(request.getPin());
        newAccount.setProfile(profile);
        Account savedAccount = repository.save(newAccount);

        CreateWalletResponse response = new CreateWalletResponse();
        response.setMessage(ACCOUNT_CREATED_SUCCESSFULLY);
        response.setAccountNumber(savedAccount.getAccountNumber());
        return response;
    }

    @Override
    public List<WalletAccountResponse> findAllAccounts() {
        if (repository.findAll().isEmpty()) return new ArrayList<>();
        return repository
                .findAll()
                .stream()
                .map(account -> modelMapper.map(account , WalletAccountResponse.class))
                .toList();
    }

    @Override
    public ProfileResponse getProfile(String accountNumber, String pin) throws AccountAlreadyExistException, AuthorizationException {
        Account account = findAccounts(accountNumber);
        if(account == null) throw new AccountDoesNotExistException(ACCOUNT_DOES_NOT_EXIST);
        if(!account.getPin().equals(pin))throw new AuthorizationException(AUTHORIZATION_MESSAGE);
        return new ProfileResponse(account);
    }

    @Override
    public PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountDoesNotExistException, InvalidTransaction {
        Account account = findAccounts(request.getAccountNumber());
        if(account == null) throw new AccountDoesNotExistException(ACCOUNT_DOES_NOT_EXIST);
        boolean isAmountInvalid = request.getAmount().compareTo(BigDecimal.valueOf(20)) < 0;
        if(isAmountInvalid) throw new InvalidTransaction(AMOUNT_LESS_THAN_FIVE);
        request.setPaymentMethod(request.getPaymentMethod().toUpperCase());
        if(!request.getPaymentMethod().equals(MONNIFY) && !request.getPaymentMethod().equals(PAYSTACK)) throw new InvalidTransaction(TRANSACTION_MEANS_NOT_EXIST);
        PerformTransactionResponse transactionResponse = new PerformTransactionResponse();
        CreateTransactionResponse createTransactionResponse = transactionService.createTransaction(new CreateTransactionRequest(request,account));
        createTransactionResponse.setTransactionId(createTransactionResponse.getTransactionId());
        if(request.getPaymentMethod().equals(PAYSTACK)){
            fundPaystackWallet(request,createTransactionResponse,account,transactionResponse);
        }else{
            fundMonnifyWallet(request,createTransactionResponse,account,transactionResponse);
        }
        transactionResponse.setMessage(TRANSACTION_SUCCESSFUL);

        return transactionResponse;
    }

    private void fundPaystackWallet(PerformTransactionRequest request,CreateTransactionResponse transactionResponse ,Account account,PerformTransactionResponse performTransactionResponse){
        request.setAmount(request.getAmount().multiply(BigDecimal.valueOf(100)));
        InitializePayment<PayStackInitializePayment> initializePayment = new  InitializePayment<>();
        initializePayment.setData(createPaystackPaymentRequest(request.getAmount(), transactionResponse.getTransactionId(), account.getProfile().getEmail()));
        InitializePaymentResponse initializePaymentResponse = payStackPaymentService.initializeTransaction(initializePayment);
        performTransactionResponse.setUrl(initializePaymentResponse.getUrl());
    }

    private void fundMonnifyWallet(PerformTransactionRequest request,CreateTransactionResponse transactionResponse ,Account account,PerformTransactionResponse performTransactionResponse){
        InitializePayment<MonnifyInitializePayment> monnifyPayment = new InitializePayment<>();
        monnifyPayment.setData(createMonnifyPaymentRequest(request,account.getAccountName(),account.getProfile().getEmail(), transactionResponse.getTransactionId()));
        InitializePaymentResponse initializePaymentResponse = monnifyPaymentService.initializeTransaction(monnifyPayment);
        performTransactionResponse.setUrl(initializePaymentResponse.getUrl());
    }
    private MonnifyInitializePayment createMonnifyPaymentRequest(PerformTransactionRequest request, String name, String email, String reference) {
        MonnifyInitializePayment monnifyPayment = new MonnifyInitializePayment();
        monnifyPayment.setAmount(request.getAmount());
        monnifyPayment.setPaymentDescription(request.getDescription());
        monnifyPayment.setPaymentReference(reference);
        monnifyPayment.setCurrencyCode(request.getCurrency());
        monnifyPayment.setCustomerName(name);
        monnifyPayment.setCustomerEmail(email);
        monnifyPayment.setContractCode(beanConfig.getMonnifyContractCode());
        return monnifyPayment;
    }

    private PayStackInitializePayment createPaystackPaymentRequest(BigDecimal amount, String reference, String email) {
        PayStackInitializePayment payStackInitializePayment = new PayStackInitializePayment();
        payStackInitializePayment.setAmount(amount);
        payStackInitializePayment.setReference(reference);
        payStackInitializePayment.setEmail(email);
        return payStackInitializePayment;
    }

    @Async
    @Override
    public void fundWallet(FundWalletRequest request) throws InvalidTransaction {
        if(request.getStatus().equals(PAYSTACK_SUCCESS) || request.getStatus().equals(MONNIFY_SUCCESS)){
            Transaction transaction = transactionService.updateTransaction(request.getReference(), Status.SUCCESSFUL);
            Account account = transaction.getAccount();
            account.setBalance(account.getBalance().add(transaction.getAmount()));
            repository.save(account);
        }else{
            transactionService.updateTransaction(request.getReference(), Status.FAILED);
        }
    }

    @Override
    public List<TransactionResponse> findAllTransaction(String accountNumber, String pin) throws AccountAlreadyExistException, AuthorizationException {
        Account account = findAccounts(accountNumber);
        if(!account.getPin().equals(pin))throw new AuthorizationException(AUTHORIZATION_MESSAGE);
        return transactionService.findAllTransactionsByAccount(account);
    }

    private Account findAccounts(String accountNumber) throws AccountAlreadyExistException {
        return repository.findByAccountNumber(accountNumber);
    }
}
