package com.app.cosmetics.core.account;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.order.Order;
import com.app.cosmetics.core.role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    private String firstName;

    private String lastName;

    private String address;

    private String avatar;

    @OneToMany(mappedBy = "account")
    private List<Order> orders = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(
                    name = "account_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    public Account(String username, String password, String email, String firstName, String lastName, String address, String avatar, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.avatar = avatar;
        this.roles = roles;
    }

    public void update(String password, String email, String firstName, String lastName, String address, String avatar) {
        if (!"".equals(password)) {
            this.password = password;
        }

        if (!"".equals(email)) {
            this.email = email;
        }

        if (!"".equals(firstName)) {
            this.firstName = firstName;
        }

        if (!"".equals(lastName)) {
            this.lastName = lastName;
        }

        if (!"".equals(address)) {
            this.address = address;
        }

        if (!"".equals(avatar)) {
            this.avatar = avatar;
        }
    }
}

