package com.app.cosmetics.core.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "discounts")
public class DiscountController {

    private final DiscountService discountService;

    @PostMapping
    public ResponseEntity<DiscountResponse> create(@Valid @RequestBody DiscountRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(discountService.create(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DiscountResponse> inactive(@PathVariable long id) {
        return ResponseEntity.ok(discountService.inactive(id));
    }
}
