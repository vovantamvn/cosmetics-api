package com.app.cosmetics.core.lot;

import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.stock.Stock;
import com.app.cosmetics.core.stock.StockRepository;
import com.app.cosmetics.helper.CreateItemHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LotRepositoryTest {

    @Autowired
    private LotRepository lotRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Test
    void it_should_remove_lot() {
        // Arrange
        Item item = CreateItemHelper.create();
        itemRepository.save(item);

        Lot lot = new Lot();
        lot.setItem(item);
        lot.setCount(15);
        lotRepository.save(lot);

        Long lotId = lot.getId();
        Long itemId = item.getId();

        // Act
        lotRepository.deleteById(lotId);

        // Assert
        assertFalse(lotRepository.existsById(lotId));
        assertTrue(itemRepository.existsById(itemId));
    }

    @Test
    void it_should_remove_lot_by_stock() {
        // Arrange
        Lot lot = new Lot();
        lot.setCount(15);

        Stock stock = new Stock();
        stock.setLot(lot);
        stock.setCount(15);

        stockRepository.save(stock);

        Long lotId = lot.getId();

        // Act
        stock.setLot(null);
        stockRepository.save(stock);

        lotRepository.deleteById(lotId);

        // Assert
        assertFalse(lotRepository.existsById(lotId));
    }
}