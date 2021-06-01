package com.app.cosmetics.core.stock;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.stockitem.StockItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Stock extends BaseEntity {

    private String name;

    private String phone;

    private int total;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<StockItem> stockItems = new ArrayList<>();
}
