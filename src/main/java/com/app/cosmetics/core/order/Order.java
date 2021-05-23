package com.app.cosmetics.core.order;

import com.app.cosmetics.core.account.Account;
import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.orderitem.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity(name = "orders")
@NoArgsConstructor
public class Order extends BaseEntity {

    @ManyToOne
    private Account account;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Lob
    private String note;

    @PositiveOrZero
    private int total;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
