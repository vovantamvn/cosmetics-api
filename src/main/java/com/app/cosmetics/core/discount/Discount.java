package com.app.cosmetics.core.discount;

import com.app.cosmetics.core.base.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Discount extends BaseEntity {
    @NotBlank
    @Column(unique = true, updatable = false)
    private String code;
    private int percent;
    private String description;
    @NotNull
    private LocalDateTime expired;
    @PositiveOrZero
    private int count;
    private boolean active;

    public Discount(String code, int percent, String description, LocalDateTime expired, int count) {
        this.code = code;
        this.percent = percent;
        this.description = description;
        this.expired = expired;
        this.count = count;
        this.active = true;
    }

    public void update(int count, boolean active) {
        this.count = count;
        this.active = active;
    }
}
