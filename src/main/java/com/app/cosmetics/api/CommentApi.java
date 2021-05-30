package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.CommentService;
import com.app.cosmetics.application.data.CommentData;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PostMapping(path = "comments")
    public ResponseEntity<CommentData> create(@Valid @RequestBody CommentRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.create(request));
    }

    @GetMapping(path = "comments/{id}")
    public ResponseEntity<CommentData> findById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findById(id));
    }

    @GetMapping(path = "items/{id}/comments")
    public ResponseEntity<List<CommentData>> findAllByItemId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.findAllByItemId(id));
    }

    @PutMapping(path = "comments/{id}")
    public ResponseEntity<CommentData> update(
            @PathVariable Long id,
            @Valid @RequestBody CommentRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.ok(commentService.update(id, request));
    }

    @DeleteMapping(path = "comments/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);

        return ResponseEntity.status(HttpStatus.OK)
                .build();
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class CommentRequest {
        @NotBlank
        private String content;

        @NotNull
        private Long itemId;
    }
}
