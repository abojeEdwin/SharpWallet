package com.SharpWallet.service;

import com.SharpWallet.data.repository.ProfileRepository;
import com.SharpWallet.data.repository.WalletAccountRepository;
import com.SharpWallet.dto.request.CreateAccountRequest;
import com.SharpWallet.dto.response.CreateWalletResponse;
import com.SharpWallet.exception.AccountAlreadyExistException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
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



}