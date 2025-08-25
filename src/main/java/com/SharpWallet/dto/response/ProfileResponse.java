package com.SharpWallet.dto.response;

import com.SharpWallet.data.model.Account;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProfileResponse {

    private String email;
    private String phoneNumber;
    private String address;
    private String firstName;
    private String lastName;
    private BigDecimal amount;

    public ProfileResponse(Account account) {
        this.email = account.getProfile().getEmail();
        this.phoneNumber = account.getProfile().getPhone();
        this.address = account.getProfile().getAddress();
        this.firstName = account.getProfile().getFirstName();
        this.lastName = account.getProfile().getLastName();
        this.amount = account.getBalance();
    }
}
