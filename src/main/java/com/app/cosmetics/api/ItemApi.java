package com.app.cosmetics.api;

import com.app.cosmetics.application.data.ItemData;
import com.app.cosmetics.application.ItemService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "items")
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    @GetMapping
    public ResponseEntity<List<ItemData>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ItemData> findById(@PathVariable long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemData> create(@Valid @RequestBody ItemRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.create(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ItemData> update(@PathVariable long id, @Valid @RequestBody ItemRequest request) {
        return ResponseEntity.ok(itemService.update(id, request));
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ItemRequest {
        @NotBlank(message = "can't blank")
        private String name;
        private String description;
        private String image;
        @PositiveOrZero
        private int count;
        @PositiveOrZero
        private int price;
        private Long branchId;
        private Long CategoryId;
    }
}
