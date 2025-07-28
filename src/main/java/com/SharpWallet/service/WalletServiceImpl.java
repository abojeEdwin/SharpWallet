package com.SharpWallet.service;

import com.SharpWallet.config.BeanConfig;
import com.SharpWallet.data.model.Account;
import com.SharpWallet.data.model.Profile;
import com.SharpWallet.data.repository.ProfileRepository;
import com.SharpWallet.data.repository.WalletAccountRepository;
import com.SharpWallet.dto.request.CreateAccountRequest;
import com.SharpWallet.dto.request.CreateProfileRequest;
import com.SharpWallet.dto.request.FundWalletRequest;
import com.SharpWallet.dto.request.PerformTransactionRequest;
import com.SharpWallet.dto.response.*;
import com.SharpWallet.exception.AccountAlreadyExistException;
import com.SharpWallet.exception.AuthorizationException;
import com.SharpWallet.exception.InvalidTransaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.SharpWallet.util.ApiUtil.ACCOUNT_ALREADY_EXIST;
import static com.SharpWallet.util.ApiUtil.ACCOUNT_CREATED_SUCCESSFULLY;

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
//    @Autowired
//    @Qualifier("monnifyPayment")
//    private PaymentService monnifyPaymentService;
//    @Autowired
//    @Qualifier("payStackService")
//    private PaymentService payStackService;
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
        return null;
    }

    @Override
    public PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountAlreadyExistException, InvalidTransaction {
        return null;
    }

    @Override
    public void fundWallet(FundWalletRequest request) throws InvalidTransaction {

    }

    @Override
    public List<TransactionResponse> findAllTransaction(String accountNumber, String pin) throws AccountAlreadyExistException, AuthorizationException {
        return List.of();
    }
    private boolean profileExist(String email, String phone) {
        return profileRepository.findProfileByEmailOrPhone(email, phone).isPresent();
    }
}
