package com.app.cosmetics.core.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemResponse {
    private Long id;

    private String name;

    private String description;

    private String image;

    private int count;

    private int price;
}
