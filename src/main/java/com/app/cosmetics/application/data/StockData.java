package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class StockData {
    private Long id;

    private String name;

    private String phone;

    private int total;

    private List<StockItemData> stockItems;
}
