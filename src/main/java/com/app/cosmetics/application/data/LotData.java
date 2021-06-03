package com.app.cosmetics.application.data;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class LotData {

    private Long id;

    private Long itemId;

    private LocalDate manufacturing;

    private LocalDate expiry;

    private int count;
}
