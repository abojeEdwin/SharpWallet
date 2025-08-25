package com.SharpWallet.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InitializePaymentResponse {

    @NotNull
    private String url;
    @NotNull
    private String reference;
    @NotNull
    private boolean status;
}
