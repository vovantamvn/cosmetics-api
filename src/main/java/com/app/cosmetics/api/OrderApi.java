package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.OrderService;
import com.app.cosmetics.application.data.OrderData;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping(path = "orders")
@RequiredArgsConstructor
public class OrderApi {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderData>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping
    public ResponseEntity<OrderData> create(@Valid @RequestBody OrderApi.OrderRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.create(request));
    }


    @Getter
    @Setter
    public static class OrderRequest {
        @NotBlank
        private String firstName;

        private String lastName;

        @NotBlank
        private String address;

        @NotBlank
        private String phone;

        @Email
        @NotBlank
        private String email;

        @Pattern(regexp = "ONLINE|OFFLINE")
        private String paymentMethod;

        private String note;

        @Valid
        @Size(min = 1, message = "items size must be great than 0")
        private List<OrderItemRequest> items;
    }

    @Getter
    @Setter
    public static class OrderItemRequest {
        @NotNull
        private Long itemId;

        @Positive
        private int count;
    }
}
