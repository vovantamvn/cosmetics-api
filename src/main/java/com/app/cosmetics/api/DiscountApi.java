package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.api.exception.NoAuthorizationException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.data.DiscountData;
import com.app.cosmetics.application.DiscountService;
import com.app.cosmetics.core.discount.DiscountRepository;
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
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "discounts")
public class DiscountApi {

    private final DiscountService discountService;
    private final DiscountRepository discountRepository;
    private final AuthorizationService authorizationService;

    @GetMapping(path = "/{id}")
    public ResponseEntity<DiscountData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(discountService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<DiscountData>> findAll() {
        return ResponseEntity.ok(discountService.findAll());
    }

    @PostMapping
    public ResponseEntity<DiscountData> create(
            @Valid @RequestBody DiscountRequest request,
            BindingResult bindingResult
    ) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        if (discountRepository.findDiscountByCode(request.getCode()).isPresent()) {
            bindingResult.rejectValue("code", "DUPLICATE", "duplicate code");
        }

        if (request.getPercent() < 0 || request.getPercent() > 100) {
            bindingResult.rejectValue("percent", "INVALID", "percent must be between 0 to 100");
        }

        if (request.getExpired().isBefore(LocalDateTime.now())) {
            bindingResult.rejectValue("expired","INVALID", "expired must be after now");
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(discountService.create(request));
    }

    @PutMapping(value = "/{id}/inactive")
    public ResponseEntity<DiscountData> inactive(@PathVariable Long id) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }
        return ResponseEntity.ok(discountService.inactive(id));
    }

    @Setter
    @Getter
    public static class DiscountRequest {
        @NotBlank
        private String code;
        @PositiveOrZero
        private int percent;
        @NotNull
        private LocalDateTime expired;
        @PositiveOrZero
        private int count;
        private String description;
    }
}
