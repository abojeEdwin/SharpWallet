package com.SharpWallet.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateAccountRequest {

    @Email
    private String email;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String pin;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String address;

}
