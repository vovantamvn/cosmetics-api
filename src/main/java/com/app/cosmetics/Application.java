package com.app.cosmetics;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.role.Role;
import com.app.cosmetics.core.role.RoleRepository;
import com.app.cosmetics.core.role.RoleType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        AccountRepository accountRepository = context.getBean(AccountRepository.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

        accountRepository.deleteAll();

        Role role = new Role(RoleType.ROLE_ADMIN);

        Account account = new Account(
                "admin",
                passwordEncoder.encode("123456"),
                "email@gmail.com",
                "Admin",
                "",
                "",
                "",
                List.of(role)
        );

//        roleRepository.save(role);
        accountRepository.save(account);
    }
}
