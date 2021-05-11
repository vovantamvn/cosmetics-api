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

    @Column(unique = true)
    @Enumerated(value = EnumType.STRING)
    private RoleType type;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.REMOVE)
    private List<Account> accounts = new ArrayList<>();

    public Role(RoleType type) {
        this.type = type;
    }
}
