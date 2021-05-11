package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AccountRepository accountRepository;

    public boolean isAdmin(User user) {
        Account account = accountRepository
                .findAccountByUsername(user.getUsername())
                .orElseThrow(NotFoundException::new);

        return account.getRoles().contains(RoleType.ROLE_ADMIN);
    }
}
