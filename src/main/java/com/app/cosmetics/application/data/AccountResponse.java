package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountResponse {
    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String address;

    private String avatar;
}
