package com.app.cosmetics.core.account;

import com.app.cosmetics.exception.InvalidException;
import com.app.cosmetics.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountResponse create(AccountRequest request) {
        if (accountRepository.findAccountByUsername(request.getUsername()).isPresent()) {
            throw new InvalidException("Username exits");
        }

        Account account = new Account();
        account.setUsername(request.getUsername());
        account.setPassword(request.getPassword());
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
     * @param id
     * @param request
     * @return
     */
    public AccountResponse update(long id, AccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);

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
        Account account = accountRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        return toResponse(account);
    }

    private AccountResponse toResponse(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
