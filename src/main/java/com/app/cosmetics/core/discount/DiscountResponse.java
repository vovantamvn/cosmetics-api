package com.app.cosmetics.core.discount;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class DiscountResponse {
    private Long id;

    private String code;

    private String description;

    private LocalDateTime expired;

    private int count;

    private DiscountType type;

    private boolean active;
}
