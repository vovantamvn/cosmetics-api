package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.StockService;
import com.app.cosmetics.application.data.StockData;
import com.app.cosmetics.core.item.ItemRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "stocks")
@RequiredArgsConstructor
public class StockApi {

    private final StockService stockService;
    private final ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity<StockData> create(@Valid @RequestBody StockRequest request, BindingResult bindingResult) {

        if (!itemRepository.existsById(request.getItemId())) {
            bindingResult.rejectValue(
                    "itemId",
                    "INVALID",
                    "itemId " + request.getItemId() + " not exists"
            );
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(stockService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<StockData>> findAll() {
        return ResponseEntity.ok(stockService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<StockData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(stockService.findById(id));
    }

    @Getter
    @Setter
    public static class StockRequest {

        @NotBlank
        private String name;

        @NotBlank
        private String address;

        @NotBlank
        private String phone;

        @NotNull
        private LocalDate manufacturing;

        @NotNull
        private LocalDate expiry;

        @Positive
        private int count;

        @PositiveOrZero
        private int price;

        @NotNull
        private Long itemId;
    }
}
