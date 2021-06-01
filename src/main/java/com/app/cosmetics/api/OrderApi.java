package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.OrderService;
import com.app.cosmetics.application.data.OrderData;
import com.app.cosmetics.core.item.ItemRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "orders")
@RequiredArgsConstructor
public class OrderApi {

    private final ItemRepository itemRepository;
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
    public ResponseEntity<OrderData> create(
            @Valid @RequestBody OrderApi.OrderRequest request,
            BindingResult bindingResult
    ) {
        List<OrderItemRequest> items = request.getItems();

        if (items == null || items.isEmpty()) {
            bindingResult.rejectValue("items", "NOT_NULL", "items must be not null");
        } else {
            for (OrderItemRequest itemBill : items) {
                if (!itemRepository.existsById(itemBill.getItemId())) {
                    bindingResult.rejectValue(
                            "items",
                            "NOT_FOUND",
                            "itemId " + itemBill.getItemId() + " not found"
                    );

                    break;
                }
            }
        }

        if (!List.of("ONLINE", "OFFLINE").contains(request.getPaymentMethod())) {
            bindingResult.rejectValue(
                    "paymentMethod",
                    "INVALID",
                    "paymentMethod must be in [ONLINE | OFFLINE]"
            );
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.create(request));
    }


    @Getter
    @Setter
    @NoArgsConstructor
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

        @NotBlank
        private String paymentMethod;

        private String note;

        private List<OrderItemRequest> items;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class OrderItemRequest {
        @NotNull
        private Long itemId;

        @PositiveOrZero
        private int count;
    }
}
