package com.app.cosmetics.api;

import com.app.cosmetics.application.data.AccountResponse;
import com.app.cosmetics.application.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "accounts")
@RequiredArgsConstructor
/**
 * CUR Account
 */
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        AccountResponse data = accountService.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(data);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> findById(@PathVariable long id) {
        AccountResponse data = accountService.findById(id);
        return ResponseEntity.ok(data);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountResponse> update(@PathVariable long id, @Valid @RequestBody AccountRequest request) {
        AccountResponse data = accountService.update(id, request);
        return ResponseEntity.ok(data);
    }
}
