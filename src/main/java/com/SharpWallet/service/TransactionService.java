package com.SharpWallet.service;

import com.SharpWallet.data.model.Account;
import com.SharpWallet.data.model.Status;
import com.SharpWallet.data.model.Transaction;
import com.SharpWallet.dto.request.CreateTransactionRequest;
import com.SharpWallet.dto.response.CreateTransactionResponse;
import com.SharpWallet.dto.response.TransactionResponse;
import com.SharpWallet.exception.InvalidTransaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    CreateTransactionResponse createTransaction(CreateTransactionRequest request);
    Transaction findTransactionById(String referenceId) throws InvalidTransaction;
    Transaction updateTransaction(String referenceId, Status status) throws InvalidTransaction;
    List<TransactionResponse> findAllTransactionsByAccount(Account account);


}
