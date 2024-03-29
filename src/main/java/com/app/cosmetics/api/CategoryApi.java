package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.api.exception.NoAuthorizationException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.CategoryService;
import com.app.cosmetics.application.data.CategoryData;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "categories")
public class CategoryApi {

    private final CategoryService categoryService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<Page<CategoryData>> findAll(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        Optional<String> optName = Optional.empty();

        if (!name.equals("")) optName = Optional.of(name);

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(categoryService.findAll(optName, pageable));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CategoryData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoryData> create(
            @Valid @RequestBody CategoryRequest request,
            BindingResult bindingResult
    ) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(request.getName()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<CategoryData> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request,
            BindingResult bindingResult
    ) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }

        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.ok(categoryService.update(id, request.getName()));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (!authorizationService.isAdmin()) {
            throw new NoAuthorizationException();
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class CategoryRequest {
        @NotBlank
        private String name;
    }
}
