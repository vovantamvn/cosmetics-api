package com.app.cosmetics.application;

import com.app.cosmetics.api.StockApi;
import com.app.cosmetics.application.data.StockData;
import com.app.cosmetics.application.data.StockItemData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.stock.Stock;
import com.app.cosmetics.core.stock.StockRepository;
import com.app.cosmetics.core.stockitem.StockItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public StockData create(StockApi.StockRequest request) {

        Stock stock = new Stock();
        stock.setName(request.getName());
        stock.setPhone(request.getPhone());

        stockRepository.save(stock);

        List<StockItem> stockItems = new ArrayList<>();

        for (StockApi.StockItemRequest itemRequest : request.getStockItems()) {
            StockItem stockItem = new StockItem();
            stockItem.setStock(stock);
            stockItem.setCount(itemRequest.getCount());
            stockItem.setItemId(itemRequest.getItemId());

            stockItems.add(stockItem);

            Item item = itemRepository.findById(itemRequest.getItemId())
                    .orElseThrow(NotFoundException::new);

            int currentCount = item.getCount() + itemRequest.getCount();

            item.setCount(currentCount);

            itemRepository.save(item);
        }

        stock.setStockItems(stockItems);

        stockRepository.save(stock);

        return toResponse(stock);
    }

    public StockData findById(long id) {
        Stock stock = stockRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(stock);
    }

    public List<StockData> findAll() {
        return stockRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private StockData toResponse(Stock stock) {
        StockData stockData = new StockData();

        stockData.setId(stock.getId());
        stockData.setName(stock.getName());
        stockData.setPhone(stock.getPhone());
        stockData.setTotal(stock.getTotal());

        List<StockItemData> stockItems = stock.getStockItems()
                .stream()
                .map(stockItem -> {
                    StockItemData stockItemData = new StockItemData();
                    stockItemData.setId(stockItem.getId());
                    stockItemData.setItemId(stockItem.getItemId());
                    stockItemData.setCount(stockItem.getCount());

                    return stockItemData;
                })
                .collect(Collectors.toList());

        stockData.setStockItems(stockItems);

        return stockData;
    }
}
