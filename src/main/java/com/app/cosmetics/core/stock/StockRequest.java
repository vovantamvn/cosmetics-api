package com.app.cosmetics.core.stock;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PositiveOrZero;

@Setter
@Getter
public class StockRequest {
    private Long itemId;

    @PositiveOrZero
    private int count;

    @PositiveOrZero
    private int price;
}
