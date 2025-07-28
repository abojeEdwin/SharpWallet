package com.SharpWallet.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonnifyFundWalletRequest {

    @NotNull
    private String eventType;
    @NotNull
    private MonnifyData eventData;
}
