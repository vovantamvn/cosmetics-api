package com.app.cosmetics.core.lot;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.stock.Stock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Lot extends BaseEntity {

    private LocalDate manufacturing;

    private LocalDate expiry;

    @PositiveOrZero
    private int count;

    @ManyToOne
    private Item item;

    @OneToOne(mappedBy = "lot")
    private Stock stock;
}
