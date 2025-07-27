package com.SharpWallet.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletAccountResponse {

    private String accountName;
    private String accountNumber;
    private String balance;

}
