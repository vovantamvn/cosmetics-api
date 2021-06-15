package com.app.cosmetics.application;

import com.app.cosmetics.api.StockApi;
import com.app.cosmetics.application.data.LotData;
import com.app.cosmetics.application.data.StockData;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.stock.Stock;
import com.app.cosmetics.core.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public StockData create(StockApi.StockRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(NotFoundException::new);

        item.setPrePrice(request.getPrice());

        itemRepository.save(item);

        Lot lot = new Lot();
        lot.setItem(item);
        lot.setManufacturing(request.getManufacturing());
        lot.setExpiry(request.getExpiry());
        lot.setCount(request.getCount());

        Stock stock = new Stock();
        stock.setName(request.getName());
        stock.setPhone(request.getPhone());
        stock.setAddress(request.getAddress());
        stock.setPrice(request.getPrice());
        stock.setCount(request.getCount());
        stock.setLot(lot);

        stockRepository.save(stock);

        return toResponse(stock);
    }

    public StockData findById(long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(stock);
    }

    public List<StockData> findAll() {
        return stockRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private StockData toResponse(Stock stock) {
        Lot lot = stock.getLot();

        LotData lotData = LotData.builder()
                .id(lot.getId())
                .itemId(lot.getItem().getId())
                .manufacturing(lot.getManufacturing())
                .expiry(lot.getExpiry())
                .count(lot.getCount())
                .build();

        return StockData.builder()
                .id(stock.getId())
                .lot(lotData)
                .name(stock.getName())
                .address(stock.getAddress())
                .phone(stock.getPhone())
                .count(stock.getCount())
                .price(stock.getPrice())
                .created(stock.getCreated())
                .build();
    }
}
