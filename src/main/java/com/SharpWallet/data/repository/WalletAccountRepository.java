package com.SharpWallet.data.repository;

import com.SharpWallet.data.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletAccountRepository extends JpaRepository<Account,Long> {

    Account findByAccountNumber(String accountNumber);

}
