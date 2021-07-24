package com.app.cosmetics.api;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UpdateAccountRequest {
    @Email
    private String email;

    @NotBlank
    private String firstName;

    private String lastName;

    private String address;

    @NotBlank
    private String avatar;
}
