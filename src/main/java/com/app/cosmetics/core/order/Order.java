package com.app.cosmetics.core.order;

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

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String email;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Lob
    private String note;

    @PositiveOrZero
    private int total;

    @PositiveOrZero
    private int totalPre;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void update(OrderStatus status) {
        this.status = status;
    }
}
