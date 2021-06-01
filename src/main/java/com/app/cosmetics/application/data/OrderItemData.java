package com.app.cosmetics.application.data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderItemData {
    private Long id;
    private String name;
    private int count;
    private int price;
    private int prePrice;
}