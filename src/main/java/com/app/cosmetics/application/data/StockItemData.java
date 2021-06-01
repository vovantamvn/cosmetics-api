package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockItemData {
    private Long id;

    private Long itemId;

    private int count;
}
