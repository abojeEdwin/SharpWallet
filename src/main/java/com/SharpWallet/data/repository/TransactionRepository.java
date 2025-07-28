package com.SharpWallet.data.repository;

import com.SharpWallet.data.model.Account;
import com.SharpWallet.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findAllByAccount(Account account);
}
