package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class DiscountData {
    private Long id;
    private String code;
    private int percent;
    private String description;
    private LocalDateTime expired;
    private int count;
    private boolean active;
}
