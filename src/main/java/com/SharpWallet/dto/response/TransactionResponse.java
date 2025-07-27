package com.SharpWallet.dto.response;


import com.SharpWallet.data.model.Status;
import com.SharpWallet.data.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionResponse {

    private BigDecimal amount;
    private String description;
    private String recipientName;
    private Status status;
    private LocalDateTime createdAt;


    public TransactionResponse(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.recipientName = transaction.getRecipientName();
        this.status = transaction.getStatus();
        this.createdAt = transaction.getCreatedAt();
    }
}
