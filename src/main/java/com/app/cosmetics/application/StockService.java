package com.app.cosmetics.application;

import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.api.exception.InvalidException;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.stock.Stock;
import com.app.cosmetics.core.stock.StockRepository;
import com.app.cosmetics.api.StockRequest;
import com.app.cosmetics.application.data.StockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ItemRepository itemRepository;

    public StockResponse create(StockRequest request) {
        Item item = itemRepository
                .findById(request.getItemId())
                .orElseThrow(() -> {
                    log.error("Item id {} invalid", request.getItemId());
                    return new InvalidException("Item ID invalid");
                });

        Stock stock = new Stock();
        stock.setItem(item);
        stock.setCount(request.getCount());
        stock.setPrice(request.getPrice());

        Stock result = stockRepository.save(stock);

        return toResponse(result);
    }

    public StockResponse findById(long id) {
        Stock stock = stockRepository
                .findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(stock);
    }

    public List<StockResponse> findAll() {
        return stockRepository
                .findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private StockResponse toResponse(Stock stock) {
        Item item = stock.getItem();

        StockResponse response = new StockResponse();
        response.setId(stock.getId());
        response.setCount(stock.getCount());
        response.setItemId(item.getId());
        response.setPrice(item.getPrice());

        return response;
    }
}
