package com.app.cosmetics.core.discount;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Setter
@Getter
public class DiscountRequest {
    private String code;

    private String description;

    private LocalDateTime expired;

    @PositiveOrZero
    private int count;

    @Enumerated(EnumType.STRING)
    private DiscountType type;
}
