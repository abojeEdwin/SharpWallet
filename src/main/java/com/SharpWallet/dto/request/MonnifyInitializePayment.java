package com.SharpWallet.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonnifyInitializePayment {
    private BigDecimal amount;
    private String customerName;
    private String customerEmail;
    private String paymentReference;
    private String paymentDescription;
    private String currencyCode;
    private String contractCode;

}
