package com.app.cosmetics.application;

import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.ItemAnalyticData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.core.orderitem.OrderItem;
import com.app.cosmetics.core.orderitem.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderItemRepository orderItemRepository;

    private final ItemRepository itemRepository;

    public List<ItemAnalyticData> getAnalyticDataList() {
        List<ItemAnalyticData> itemAnalyticDataList = new ArrayList<>();

        List<OrderItem> allBeforeSixMonths = orderItemRepository.findAllBeforeSixMonths();

        Map<Long, Integer> listMap = new HashMap<>();

        for (OrderItem orderItem : allBeforeSixMonths) {
            long key = orderItem.getItem().getId();
            int cost = listMap.getOrDefault(key, 0);
            int newCost = cost + orderItem.getCount() * (orderItem.getPrice() - orderItem.getPrePrice());

            listMap.put(key, newCost);
        }

        for (long key : listMap.keySet()) {
            Item item = itemRepository.findById(key).orElseThrow(NotFoundException::new);
            ItemAnalyticData itemAnalyticData = new ItemAnalyticData();
            itemAnalyticData.setName(item.getName());
            itemAnalyticData.setData(listMap.get(key));

            itemAnalyticDataList.add(itemAnalyticData);
        }

        return itemAnalyticDataList;
    }
}
