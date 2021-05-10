package com.app.cosmetics.core.item;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.stock.Stock;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item extends BaseEntity {
    @NotBlank
    private String name;

    private String description;

    private String image;

    @PositiveOrZero
    private int count;

    @PositiveOrZero
    private int price;

    @OneToMany(mappedBy = "item")
    private List<Stock> stocks = new ArrayList<>();

    @ManyToOne
    private Branch branch;
}
