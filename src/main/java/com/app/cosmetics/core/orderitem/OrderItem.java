package com.app.cosmetics.core.orderitem;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne
    private Item item;

    private int count;

    private int price;

    private int prePrice;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Order order;

    public OrderItem(Item item, int count, int price, int prePrice, Order order) {
        this.item = item;
        this.count = count;
        this.price = price;
        this.prePrice = prePrice;
        this.order = order;
    }
}
