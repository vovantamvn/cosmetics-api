package com.app.cosmetics;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.account.AccountRepository;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.category.CategoryRepository;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
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

import java.time.LocalDate;
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
        fakeItem(context);
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
                "Thao",
                "Minh",
                "Ha Noi, Viet Nam",
                "",
                roles
        );

        accountRepository.save(account);

        log.info("Fake data account success");
    }

    private void fakeItem(ConfigurableApplicationContext context) {
        BranchRepository branchRepository = context.getBean(BranchRepository.class);
        CategoryRepository categoryRepository = context.getBean(CategoryRepository.class);
        ItemRepository itemRepository = context.getBean(ItemRepository.class);

        Category category = new Category("Kem dưỡng da");
        Branch branch = new Branch("Pond");

        Item item = new Item();
        item.setName("Kem dưỡng da Pond");
        item.setDescription("Đây là kem dưỡng da số 1");
        item.setCount(100);
        item.setPrice(50_000);
        item.setPrice(80_000);
        item.setTypes(List.of("30ml", "50ml"));
        item.setExpiry(LocalDate.of(2023, 06, 06));
        item.setBranch(branch);
        item.setCategory(category);

        itemRepository.deleteAll();
        categoryRepository.deleteAll();
        branchRepository.deleteAll();

        categoryRepository.save(category);
        branchRepository.save(branch);
        itemRepository.save(item);

        log.info("Fake category, brand, item success");
    }
}
