package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class PayStackInitializePayment {

    @NotNull
    private String email;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String reference;
}
