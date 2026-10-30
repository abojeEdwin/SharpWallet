package com.SharpWallet.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CreateAccountRequest {

    @Email
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    @Pattern(regexp = "\\d{4,6}", message = "PIN must contain 4 to 6 digits")
    private String pin;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String address;

}
