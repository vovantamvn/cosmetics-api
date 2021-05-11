package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AccountRepository accountRepository;

    public boolean isAdmin() {
        Optional<Account> opt = getCurrentAccount();
        if (opt.isEmpty()) {
            return false;
        }

        Account account = opt.get();

        System.out.println(account.getRoles());

        return false;
    }

    private Optional<Account> getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        System.out.println("getCurrentAccount");
        System.out.println(username);

        return accountRepository.findAccountByUsername(username);
    }
}
