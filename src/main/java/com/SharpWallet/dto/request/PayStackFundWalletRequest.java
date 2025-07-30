package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayStackFundWalletRequest {

    @NotNull
    private PayStackData data;
    @NotNull
    private String event;
}
