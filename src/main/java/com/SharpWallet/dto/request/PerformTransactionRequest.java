package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PerformTransactionRequest {

    @NotNull
    private String accountNumber;
    @NotNull
    private String description;
    @NotNull
    private String paymentMethod;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String currency;
    @NotNull
    private String recipientName;
}
