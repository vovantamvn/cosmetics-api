package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.api.exception.NoAuthorizationException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.data.ItemData;
import com.app.cosmetics.application.ItemService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "items")
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<ItemData>> findAll() {
        return ResponseEntity.ok(itemService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ItemData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(itemService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ItemData> create(@Valid @RequestBody ItemRequest request, BindingResult bindingResult) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(itemService.create(request));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ItemData> update(
            @PathVariable Long id,
            @Valid @RequestBody ItemRequest request,
            BindingResult bindingResult
    ) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.ok(itemService.update(id, request));
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ItemRequest {

        @NotBlank
        private String name;

        private String description;

        private String image;

        @PositiveOrZero
        private int count;

        @PositiveOrZero
        private int price;

        @PositiveOrZero
        private int prePrice;

        @NotNull
        private LocalDate expiry;

        private Long branchId;

        private Long CategoryId;
    }
}
