package com.app.cosmetics.application.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class StockData {
    private Long id;

    private String name;

    private String phone;

    private String address;

    private int count;

    private int price;

    private LocalDateTime created;

    private LotData lot;
}
