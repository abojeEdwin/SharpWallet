package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FundWalletRequest {

    @NotNull
    private String status;
    @NotNull
    private String reference;

    public FundWalletRequest(OpayFundWalletRequest request){
        this.status = request.getEvent();
        this.reference = request.getData().getReference();
    }

    public FundWalletRequest(AlatPayFundWalletRequest request) {
        this.status = request.getEventType();
        this.reference = request.getEventData().getPaymentReference();
    }


}
