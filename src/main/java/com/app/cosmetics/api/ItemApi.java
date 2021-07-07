package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.api.exception.NoAuthorizationException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.data.ItemData;
import com.app.cosmetics.application.ItemService;
import com.app.cosmetics.core.branch.BranchRepository;
import com.app.cosmetics.core.category.CategoryRepository;

import lombok.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.util.List;

@RestController
@RequestMapping(path = "items")
@RequiredArgsConstructor
public class ItemApi {

    private final ItemService itemService;

    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<ItemData>> findAll(@RequestParam(defaultValue = "none") String sort) {
        return ResponseEntity.ok(itemService.findAll(sort));
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

        validateRequest(request, bindingResult);

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

        validateRequest(request, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.ok(itemService.update(id, request));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        itemService.delete(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    /**
     * Validate brandId, categoryId
     */
    private final BranchRepository branchRepository;
    private final CategoryRepository categoryRepository;

    private void validateRequest(ItemRequest request, BindingResult bindingResult) {
        if (!branchRepository.existsById(request.getBranchId())) {
            bindingResult.rejectValue("branchId", "NOT_EXISTS", "branchId must be exists");
        }

        if (!categoryRepository.existsById(request.getCategoryId())) {
            bindingResult.rejectValue("categoryId", "NOT_EXISTS", "categoryId must be exists");
        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class ItemRequest {

        @NotBlank
        private String name;

        private String description;

        @Size(min = 1)
        private List<String> images;

        @PositiveOrZero
        private int price;

        @PositiveOrZero
        private int discountPrice;

        @NotNull
        private List<String> types;

        private Long branchId;

        private Long categoryId;
    }
}
