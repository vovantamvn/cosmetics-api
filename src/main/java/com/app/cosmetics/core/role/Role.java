package com.app.cosmetics.core.role;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Role extends BaseEntity {

    @Enumerated(value = EnumType.STRING)
    private RoleType type;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )
    private List<Account> accounts = new ArrayList<>();

    public Role(RoleType type) {
        this.type = type;
    }
}
