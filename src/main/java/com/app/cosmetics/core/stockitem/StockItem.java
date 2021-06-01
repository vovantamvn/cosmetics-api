package com.app.cosmetics.core.stockitem;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.stock.Stock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class StockItem extends BaseEntity {

    private Long itemId;

    private int count;

    @ManyToOne
    private Stock stock;
}
