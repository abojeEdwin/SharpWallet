package com.SharpWallet.service;

import com.SharpWallet.dto.request.*;
import com.SharpWallet.dto.response.CreateWalletResponse;
import com.SharpWallet.dto.response.PerformTransactionResponse;
import com.SharpWallet.dto.response.ProfileResponse;
import com.SharpWallet.dto.response.TransactionResponse;
import com.SharpWallet.exception.AccountAlreadyExistException;
import com.SharpWallet.exception.AuthorizationException;
import com.SharpWallet.exception.InvalidTransaction;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static com.SharpWallet.util.ApiUtil.PAYSTACK_SUCCESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class WalletServiceTest {


    @Autowired
    private WalletServiceImpl walletService;

    @Test
    public void testCreateAccount() throws AccountAlreadyExistException{
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("Jake");
        request.setLastName("Samuel");
        request.setEmail("abward@gmail.com");
        request.setPin("123456");
        request.setPhoneNumber("09096041567");
        request.setAddress("17 Olatunji street");
        CreateWalletResponse response = walletService.createAccount(request);
        assertNotNull(response);
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
    }

    @Test
    public void testCreatedAccountCanFindAllAccounts() throws AccountAlreadyExistException{
        int numberOfAccounts = walletService.findAllAccounts().size();
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("Jake");
        request.setLastName("Samuel");
        request.setEmail("jega@gmail.com");
        request.setPin("123456");
        request.setPhoneNumber("09096041500");
        request.setAddress("17 Olatunji street");
        CreateWalletResponse response = walletService.createAccount(request);
        assertNotNull(response);
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
        assertThat(walletService.findAllAccounts().size()).isGreaterThan(numberOfAccounts);
    }

    @Test
    @Sql(scripts = {"/script/insert.sql"})
    public void testThatAccountAlreadyExistByEmailOrPhoneNumberCannotBeCreatedTwice() throws AccountAlreadyExistException {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("Aboje");
        request.setLastName("Edwin");
        request.setEmail("abojeedwin@gmail.com");
        request.setPin("123456");
        request.setPhoneNumber("09056424235");
        request.setAddress("17 Olatunji street");
        assertThrows(AccountAlreadyExistException.class,()->walletService.createAccount(request));
    }

    @Test
    @Sql(scripts = {"/script/insert.sql"})
    public void testThatAccountAlreadyExistByPhoneNumberCannotBeCreatedTwice() throws AccountAlreadyExistException {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("Aboje");
        request.setLastName("Edwin");
        request.setEmail("newmail@gmail.com");
        request.setPin("123456");
        request.setPhoneNumber("09096042212");
        request.setAddress("17 Olatunji street");
        assertThrows(AccountAlreadyExistException.class,()->walletService.createAccount(request));

    }

    @Test
    public void testAccountBeenCreatedCanGetProfile() throws AccountAlreadyExistException , AuthorizationException {
        CreateAccountRequest request = new CreateAccountRequest();
        request.setFirstName("Ola");
        request.setLastName("Samuel");
        request.setEmail("joblac@gmail.com");
        request.setPin("123456");
        request.setPhoneNumber("09165255306");
        request.setAddress("17 Olatunji street");
        CreateWalletResponse response = walletService.createAccount(request);
        assertNotNull(response);
        assertThat(response.getAccountNumber()).isEqualTo(request.getPhoneNumber());
        ProfileResponse profileResponse = walletService.getProfile(response.getAccountNumber(),request.getPin()) ;
        assertNotNull(profileResponse);
        assertThat(profileResponse.getEmail()).isEqualTo(request.getEmail());
        assertThat(profileResponse.getPhoneNumber()).isEqualTo(request.getPhoneNumber());

    }

    @Test
    @Sql(scripts = {"/script/insert.sql"})
    public void testThatAClientCanPerformATransaction() throws AccountAlreadyExistException, InvalidTransaction {
        PerformTransactionRequest request = new  PerformTransactionRequest();
        request.setAmount(BigDecimal.valueOf(1500.00));
        request.setCurrency("NGN");
        request.setAccountNumber("09096042212");
        request.setDescription("Shopping");
        request.setPaymentMethod("PAYSTACK");
        request.setRecipientName("Mary");
        PerformTransactionResponse response = walletService.performTransaction(request);
        log.info("response:{}",response);
        assertNotNull(response);
        assertThat(response.getUrl()).isNotNull();
        assertThat(response.getMessage()).isNotNull();

    }

    @Test
    @Sql(scripts = {"/script/insert.sql"})
    public void testThatATransactionCanBeUpdatedAndAlsoTheAccountBalanceCanBeUpdatedIfSuccessful() throws AccountAlreadyExistException, InvalidTransaction, AuthorizationException {
        ProfileResponse profileResponse = walletService.getProfile("09096042212","12345");
        PayStackFundWalletRequest request = new PayStackFundWalletRequest();
        PayStackData data = new PayStackData();
        data.setReference("1");
        request.setData(data);
        request.setEvent(PAYSTACK_SUCCESS);
        walletService.fundWallet(new FundWalletRequest(request));
        assertThat(walletService.getProfile("09096042212","12345").getAmount()).isGreaterThan(profileResponse.getAmount());
    }

    @Test
    @Sql(scripts = {"/script/insert.sql"})
    public void testFindAllTransactionsByAccountNumber() throws AccountAlreadyExistException, InvalidTransaction, AuthorizationException {
        List<TransactionResponse>listOfTransactions = walletService.findAllTransaction("09096042212","12345");
        assertThat(listOfTransactions).hasSize(1);
        assertEquals(listOfTransactions.get(0).getDescription(),"Bills");
    }






}