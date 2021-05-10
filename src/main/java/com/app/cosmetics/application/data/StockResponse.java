package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockResponse {
    private Long id;

    private int count;

    private int price;

    private Long itemId;
}
