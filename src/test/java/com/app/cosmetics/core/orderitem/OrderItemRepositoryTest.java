package com.app.cosmetics.core.orderitem;

import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.helper.CreateItemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void setUp() {
        orderItemRepository.deleteAll();
        itemRepository.deleteAll();
    }

    @Test
    void findAllBeforeSixMonths() {
        // Arrange
        final LocalDateTime sixMonths = LocalDateTime.now().minusMonths(6);
        Item item = CreateItemHelper.create();
        itemRepository.save(item);

        orderItemRepository.saveAll(createOrderItems(item));

        // Act
        List<OrderItem> actual = orderItemRepository.findAllBeforeSixMonths();

        // Assert
        assertTrue(actual.size() > 0);
        actual.forEach(i -> assertTrue(sixMonths.isBefore(i.getCreated())));
    }

    private List<OrderItem> createOrderItems(Item item) {
        List<OrderItem> orderItems = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 10; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreated(now.minusMonths(i));
            orderItem.setItem(item);
            orderItem.setCount(2);
            orderItem.setPrice(10_000);
            orderItem.setPrePrice(8_000);

            orderItems.add(orderItem);
        }

        return orderItems;
    }
}