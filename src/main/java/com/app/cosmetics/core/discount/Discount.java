package com.app.cosmetics.core.discount;

import com.app.cosmetics.core.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Discount extends BaseEntity {
    @NotBlank
    @Column(unique = true, updatable = false)
    private String code;

    private String description;

    private LocalDateTime expired;

    @PositiveOrZero
    private int count;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    private boolean active;
}
