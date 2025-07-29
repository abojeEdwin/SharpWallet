package com.SharpWallet.service;

import com.SharpWallet.data.model.Account;
import com.SharpWallet.data.model.Status;
import com.SharpWallet.data.model.Transaction;
import com.SharpWallet.data.repository.TransactionRepository;
import com.SharpWallet.dto.request.CreateTransactionRequest;
import com.SharpWallet.dto.response.CreateTransactionResponse;
import com.SharpWallet.dto.response.TransactionResponse;
import com.SharpWallet.exception.InvalidTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public CreateTransactionResponse createTransaction(CreateTransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setAmount(request.getAmount());
        transaction.setDescription(request.getDescription());
        transaction.setRecipientName(request.getRecipientName());
        transaction.setDescription(request.getDescription());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setStatus(Status.PROCESSING);
        transaction.setAccount(request.getAccount());
        Transaction savedTransaction = transactionRepository.save(transaction);
        CreateTransactionResponse response = new CreateTransactionResponse();
        response.setTransactionId(savedTransaction.getId());
        return response;
    }

    @Override
    public Transaction findTransactionById(String referenceId) throws InvalidTransaction {
        return null;
    }

    @Override
    public Transaction updateTransaction(String referenceId, Status status) throws InvalidTransaction {
        return null;
    }

    @Override
    public List<TransactionResponse> findAllTransactionsByAccount(Account account) {
        return List.of();
    }
}
