package com.app.cosmetics.core.order;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.orderitem.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Order(Account account, OrderStatus status, int total, List<OrderItem> orderItems) {
        this.account = account;
        this.status = status;
        this.total = total;
        this.orderItems = orderItems;
    }

    public void update(OrderStatus status) {
        this.status = status;
    }
}
