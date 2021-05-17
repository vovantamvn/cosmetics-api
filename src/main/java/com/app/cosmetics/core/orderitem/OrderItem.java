package com.app.cosmetics.core.orderitem;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Entity
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne
    private Item item;

    private int count;

    private int price;

    @ManyToOne
    private Order order;

    public OrderItem(Item item, int count, int price) {
        this.item = item;
        this.count = count;
        this.price = price;
    }
}
