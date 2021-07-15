package com.app.cosmetics.application;

import com.app.cosmetics.application.data.ItemAnalyticData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.orderitem.OrderItem;
import com.app.cosmetics.core.orderitem.OrderItemRepository;
import com.app.cosmetics.helper.CreateItemHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AnalyticsServiceTest {

    @Autowired
    private AnalyticsService analyticsService;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private OrderItemRepository orderItemRepository;

    private final long itemId = 1;

    private Item item;

    private List<OrderItem> orderItems;

    @BeforeEach
    void setUp() {
        item = CreateItemHelper.create();
        item.setId(itemId);

        orderItems = createOrderItems(item);
    }

    @Test
    void getAnalyticDataList() {
        // Arrange
        Mockito.when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        Mockito.when(orderItemRepository.findAllBeforeSixMonths()).thenReturn(orderItems);

        // Act
        List<ItemAnalyticData> analyticDataList = analyticsService.getAnalyticDataList();

        // Assert
        assertEquals(1, analyticDataList.size());

        ItemAnalyticData itemAnalyticData = analyticDataList.get(0);

        assertEquals("Kem dưỡng da Pond", itemAnalyticData.getName());
        assertEquals(7 * 20_000, itemAnalyticData.getData());
    }

    private List<OrderItem> createOrderItems(Item item) {
        List<OrderItem> orderItems = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();

        for (int i = 0; i < 7; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setCreated(now.minusMonths(i));
            orderItem.setItem(item);
            orderItem.setCount(2);
            orderItem.setPrice(20_000);
            orderItem.setPrePrice(10_000);

            orderItems.add(orderItem);
        }

        return orderItems;
    }
}