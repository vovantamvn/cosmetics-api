package com.app.cosmetics.security;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> opt = accountRepository.findAccountByUsername(username);

        System.out.println(username);

        if (opt.isPresent()) {
            Account account = opt.get();

            return User
                    .withUsername(username)
                    .password(account.getPassword())
                    .authorities(new ArrayList<>())
                    .build();
        }

        throw new UsernameNotFoundException(username);
    }
}
