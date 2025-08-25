package com.SharpWallet.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateProfileRequest {

    @Email
    private String email;
    @NotNull
    private String phone;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String address;

}
