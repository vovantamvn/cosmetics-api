package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.LotData;
import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.lot.LotRepository;
import com.app.cosmetics.core.stock.Stock;
import com.app.cosmetics.core.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotService {

    private final LotRepository lotRepository;
    private final StockRepository stockRepository;

    public List<LotData> getLotExpired() {
        int days = 30;
        LocalDate now = LocalDate.now();
        LocalDate expireDay = now.plusDays(days);

        return lotRepository.findAll().stream()
                .filter(lot -> lot.getExpiry().isBefore(expireDay))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        Lot lot = lotRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        Stock stock = lot.getStock();
        stock.setLot(null);

        stockRepository.save(stock);
        lotRepository.delete(lot);
    }

    private LotData toResponse(Lot lot) {
        return LotData.builder()
                .id(lot.getId())
                .itemId(lot.getItem().getId())
                .count(lot.getCount())
                .expiry(lot.getExpiry())
                .manufacturing(lot.getManufacturing())
                .build();
    }
}
