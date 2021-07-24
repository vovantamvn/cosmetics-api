package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.api.exception.MyException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.data.AccountResponse;
import com.app.cosmetics.application.AccountService;
import com.app.cosmetics.core.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "accounts")
@RequiredArgsConstructor
/**
 * CUR Account
 */
public class AccountApi {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final AuthorizationService authorizationService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(
            @Valid @RequestBody CreateAccountRequest request,
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

    @GetMapping(value = "/profile")
    public ResponseEntity<AccountResponse> findProfile() {
        Optional<String> optUsername = authorizationService.getCurrentUsername();

        if (optUsername.isEmpty()) {
            throw new MyException(HttpStatus.BAD_REQUEST, "You are anonymousUser");
        }

        AccountResponse data = accountService.findByUsername(optUsername.get());

        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/profile")
    public ResponseEntity<AccountResponse> update(
            @Valid @RequestBody UpdateAccountRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        Optional<String> optUsername = authorizationService.getCurrentUsername();

        if (optUsername.isEmpty()) {
            throw new MyException(HttpStatus.BAD_REQUEST, "You are anonymousUser");
        }

        AccountResponse data = accountService.update(optUsername.get(), request);

        return ResponseEntity.ok(data);
    }
}
