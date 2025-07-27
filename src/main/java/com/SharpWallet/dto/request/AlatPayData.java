package com.SharpWallet.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlatPayData {

    @NotNull
    private String paymentReference;

}
