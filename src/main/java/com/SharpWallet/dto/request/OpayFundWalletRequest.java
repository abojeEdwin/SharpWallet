package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OpayFundWalletRequest {

    @NotNull
    private OpayData data;

    @NotNull
    private String event;
}
