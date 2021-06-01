package com.app.cosmetics.core.stock;

import com.app.cosmetics.core.stockitem.StockItem;
import com.app.cosmetics.core.stockitem.StockItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockRepositoryTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockItemRepository stockItemRepository;

    @Test
    void it_should_save_stock_items_when_save_stock() {
        // Arrange
        StockItem stockItem = new StockItem();
        stockItem.setItemId(1l);
        stockItem.setCount(15);

        List<StockItem> stockItems = Arrays.asList(stockItem);

        Stock stock = new Stock();
        stock.setName("name");
        stock.setTotal(225);
        stock.setPhone("0859292354");
        stock.setStockItems(stockItems);

        // Act
        stockRepository.save(stock);

        // Assert
        assertEquals(1, stock.getStockItems().size());

        StockItem item = stock.getStockItems().get(0);

        assertEquals(1l, item.getItemId());
        assertEquals(15, item.getCount());
    }
}