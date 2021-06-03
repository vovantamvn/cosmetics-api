package com.app.cosmetics.core.stock;

import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.lot.LotRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private LotRepository lotRepository;

    @Test
    void it_should_save_lot_when_save_stock() {
        // Arrange
        Lot lot = new Lot();
        lot.setCount(15);
        lot.setManufacturing(LocalDate.now());
        lot.setExpiry(LocalDate.now());

        Stock stock = new Stock();
        stock.setLot(lot);
        stock.setCount(15);
        stock.setPrice(150_000);

        // Act
        stockRepository.save(stock);

        Long lotId = lot.getId();

        Lot result = lotRepository.findById(lotId)
                .orElseThrow();

        // Assert
        assertEquals(15, result.getCount());
        assertEquals(LocalDate.now(), result.getManufacturing());
        assertEquals(LocalDate.now(), result.getExpiry());
    }
}