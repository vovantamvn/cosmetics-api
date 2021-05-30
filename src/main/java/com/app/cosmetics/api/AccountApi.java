package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.data.AccountResponse;
import com.app.cosmetics.application.AccountService;
import com.app.cosmetics.core.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "accounts")
@RequiredArgsConstructor
/**
 * CUR Account
 */
public class AccountApi {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody AccountRequest request,
            BindingResult bindingResult
    ) {
        if (accountRepository.findAccountByEmail(request.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "DUPLICATE", "email exists");
        }

        if (accountRepository.findAccountByUsername(request.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "DUPLICATE", "username exists");
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        AccountResponse data = accountService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable Long id) {
        AccountResponse data = accountService.findById(id);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable Long id, @Valid @RequestBody AccountRequest request) {
        AccountResponse data = accountService.update(id, request);
        return ResponseEntity.ok(data);
    }
}
