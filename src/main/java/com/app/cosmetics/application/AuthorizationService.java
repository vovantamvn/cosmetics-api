package com.app.cosmetics.application;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.role.Role;
import com.app.cosmetics.core.role.RoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        for (Role role : account.getRoles()) {
            if (role.getType() == RoleType.ROLE_ADMIN) {
                return true;
            }
        }

        return false;
    }

    private Optional<Account> getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        return accountRepository.findAccountByUsername(username);
    }

    public Optional<String> getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        if (username.equals("anonymousUser")) return Optional.empty();

        return Optional.of(username);
    }
}
