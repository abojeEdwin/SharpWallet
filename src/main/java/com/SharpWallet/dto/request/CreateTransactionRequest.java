package com.SharpWallet.dto.request;


import com.SharpWallet.data.model.Account;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateTransactionRequest {


    @NotNull
    private String description;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Account account;
    @NotNull
    private String recipientName;

    public CreateTransactionRequest(PerformTransactionRequest request, Account account) {
        this.description = request.getDescription();
        this.amount = request.getAmount();
        this.account = account;
        this.recipientName = request.getRecipientName();
    }


}
