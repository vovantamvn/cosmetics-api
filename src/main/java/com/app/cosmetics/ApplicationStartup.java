package com.app.cosmetics;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.role.Role;
import com.app.cosmetics.core.role.RoleRepository;
import com.app.cosmetics.core.role.RoleType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${resetdata}")
    private boolean resetDatabase;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!resetDatabase) {
            return;
        }

        ConfigurableApplicationContext context = event.getApplicationContext();
        fakeRole(context);
        fakeAccount(context);
    }

    private void fakeRole(ConfigurableApplicationContext context) {
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        roleRepository.deleteAll();

        List.of(
                new Role(RoleType.ROLE_ADMIN),
                new Role(RoleType.ROLE_EMPLOYEE),
                new Role(RoleType.ROLE_USER)
        ).forEach(role -> roleRepository.save(role));

        log.info("Fake role success");
    }

    private void fakeAccount(ConfigurableApplicationContext context) {
        AccountRepository accountRepository = context.getBean(AccountRepository.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);

        List<Role> roles = roleRepository.findAll();

        accountRepository.deleteAll();

        Account account = new Account(
                "admin",
                passwordEncoder.encode("123456"),
                "admin@gmail.com",
                "Admin",
                "",
                "Ha Noi, Viet Nam",
                "",
                roles
        );

        accountRepository.save(account);

        log.info("Fake data account success");
    }
}
