package com.app.cosmetics.core.item;

import com.app.cosmetics.core.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

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
}
