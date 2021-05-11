package com.app.cosmetics.application.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ItemData {
    private Long id;
    private String name;
    private String description;
    private String image;
    private int count;
    private int price;
    private BranchData branch;
    private CategoryData category;
}

