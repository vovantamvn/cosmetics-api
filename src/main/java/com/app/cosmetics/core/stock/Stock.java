package com.app.cosmetics.core.stock;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.lot.Lot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
@Entity
public class Stock extends BaseEntity {

    private String name;

    private String phone;

    private String address;

    @Positive
    private int count;

    @PositiveOrZero
    private int price;

    @OneToOne(cascade = CascadeType.ALL)
    private Lot lot;
}
