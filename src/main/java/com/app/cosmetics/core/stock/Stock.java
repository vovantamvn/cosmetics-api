package com.app.cosmetics.core.stock;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@Entity
public class Stock extends BaseEntity {
    @PositiveOrZero
    private int count;

    @PositiveOrZero
    private int price;

    @ManyToOne
    private Item item;
}
