package com.SharpWallet.service;

import com.SharpWallet.dto.request.CreateAccountRequest;
import com.SharpWallet.dto.request.FundWalletRequest;
import com.SharpWallet.dto.request.PerformTransactionRequest;
import com.SharpWallet.dto.response.*;
import com.SharpWallet.exception.AccountAlreadyExistException;
import com.SharpWallet.exception.AuthorizationException;
import com.SharpWallet.exception.InvalidTransaction;


import java.util.List;

public interface WalletService {


    CreateWalletResponse createAccount(CreateAccountRequest request) throws  AccountAlreadyExistException;
    List<WalletAccountResponse> findAllAccounts();
    ProfileResponse getProfile(String accountNumber, String pin) throws AccountAlreadyExistException, AuthorizationException;
    PerformTransactionResponse performTransaction(PerformTransactionRequest request) throws AccountAlreadyExistException, InvalidTransaction;
    void fundWallet(FundWalletRequest request) throws InvalidTransaction;
    List<TransactionResponse> findAllTransaction(String accountNumber, String pin) throws AccountAlreadyExistException, AuthorizationException;

}
