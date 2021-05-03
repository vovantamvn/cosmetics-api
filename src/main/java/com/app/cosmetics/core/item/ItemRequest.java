package com.app.cosmetics.core.item;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class ItemRequest {
    @NotBlank
    private String name;

    private String description;

    private String image;

    @PositiveOrZero
    private int count;

    @PositiveOrZero
    private int price;
}
