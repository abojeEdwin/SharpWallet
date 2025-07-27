package com.SharpWallet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlatPayFundWalletRequest {

    @NotNull
    private String eventType;
    @NotNull
    private AlatPayData eventData;
}
