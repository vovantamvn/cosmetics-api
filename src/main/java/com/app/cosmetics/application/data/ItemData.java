package com.app.cosmetics.application.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class ItemData {

    private Long id;

    private String name;

    private String description;

    private String image;

    private int count;

    private int price;

    private int prePrice;

    private int discountPrice;

    private LocalDate expiry;

    private List<String> types;

    private BranchData branch;

    private CategoryData category;
}

