package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NoAuthorizationException;
import com.app.cosmetics.application.data.AccountResponse;
import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.api.AccountRequest;
import com.app.cosmetics.api.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public AccountResponse create(AccountRequest request) {
        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(passwordEncoder.encode(request.getPassword()));
        account.setEmail(request.getEmail());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAvatar(request.getAvatar());
        account.setAddress(request.getAddress());

        Account result = accountRepository.save(account);

        log.info("Create account {} success", account.getUsername());

        return toResponse(result);
    }

    /**
     * Change email, firstName, lastName, avatar, address
     */
    public AccountResponse update(Long id, AccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ( !username.equals(account.getUsername()) ) {
            throw new NoAuthorizationException();
        }

        account.setEmail(request.getEmail());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAvatar(request.getAvatar());
        account.setAddress(request.getAddress());

        Account result = accountRepository.save(account);

        log.info("Update account {} success", account.getUsername());

        return toResponse(result);
    }

    public AccountResponse findById(long id) {
        Account account = accountRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(account);
    }

    private AccountResponse toResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
