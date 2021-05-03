package com.app.cosmetics.core.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public AccountResponse create(AccountRequest request) {
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

        return convert(result);
    }

    /**
     * Change email, firstName, lastName, avatar, address
     * @param id
     * @param request
     * @return
     */
    public AccountResponse update(long id, AccountRequest request) {
        Optional<Account> opt = accountRepository.findById(id);

        if (opt.isEmpty()) {
            throw new RuntimeException();
        }

        Account account = opt.get();
        account.setEmail(request.getEmail());
        account.setFirstName(request.getFirstName());
        account.setLastName(request.getLastName());
        account.setAvatar(request.getAvatar());
        account.setAddress(request.getAddress());

        Account result = accountRepository.save(account);

        log.info("Update account {} success", account.getUsername());

        return convert(result);
    }

    public AccountResponse findById(long id) {
        Optional<Account> opt = accountRepository.findById(id);
        if (opt.isPresent()) {
            return convert(opt.get());
        }

        throw new RuntimeException();
    }

    private AccountResponse convert(Account account) {
        return modelMapper.map(account, AccountResponse.class);
    }
}
