package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.MyException;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.lot.LotRepository;
import com.app.cosmetics.helper.CreateItemHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private LotRepository lotRepository;

    @Test
    void it_should_buy_success_item_when_enough_count() {
        // Arrange
        Item item = CreateItemHelper.create();
        itemRepository.save(item);

        Lot lot = new Lot();
        lot.setItem(item);
        lot.setCount(10);
        lotRepository.save(lot);

        Long itemId = item.getId();
        Long lotId = lot.getId();

        // Act
        orderService.buyItem(itemId, 5);

        // Assert
        Lot result = lotRepository.findById(lotId)
                .orElseThrow();

        assertEquals(5, result.getCount());
    }

    @Test
    void it_should_buy_success_item_when_enough_count_multiple_lots() {
        // Arrange
        Item item = CreateItemHelper.create();
        itemRepository.save(item);

        Lot lotOne = new Lot();
        lotOne.setItem(item);
        lotOne.setCount(10);
        lotRepository.save(lotOne);

        Lot lotTwo = new Lot();
        lotTwo.setItem(item);
        lotTwo.setCount(10);
        lotRepository.save(lotTwo);

        Long itemId = item.getId();
        Long lotOneId = lotOne.getId();
        Long lotTwoId = lotTwo.getId();

        // Act
        orderService.buyItem(itemId, 15);

        // Assert
        Lot resultOne = lotRepository.findById(lotOneId)
                .orElseThrow();

        Lot resultTwo = lotRepository.findById(lotTwoId)
                .orElseThrow();

        assertEquals(5, resultOne.getCount() + resultTwo.getCount());
    }

    @Test
    void it_should_throw_exception_when_count_not_enough() {
        // Arrange
        Item item = CreateItemHelper.create();
        itemRepository.save(item);

        Lot lotOne = new Lot();
        lotOne.setItem(item);
        lotOne.setCount(10);
        lotRepository.save(lotOne);

        Lot lotTwo = new Lot();
        lotTwo.setItem(item);
        lotTwo.setCount(15);
        lotRepository.save(lotTwo);

        Long itemId = item.getId();
        Long lotOneId = lotOne.getId();
        Long lotTwoId = lotTwo.getId();

        // Act

        // Assert
        assertThrows(MyException.class, () -> orderService.buyItem(itemId, 35));

        Lot resultOne = lotRepository.findById(lotOneId)
                .orElseThrow();

        Lot resultTwo = lotRepository.findById(lotTwoId)
                .orElseThrow();

        assertEquals(10, resultOne.getCount());
        assertEquals(15, resultTwo.getCount());
    }
}