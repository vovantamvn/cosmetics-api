package com.app.cosmetics.core.branch;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "branches")
public class BranchController {

    private final BranchService branchService;

    @GetMapping
    public ResponseEntity<List<BranchResponse>> findAll() {
        return ResponseEntity.ok(branchService.findAll());
    }

    @PostMapping
    public ResponseEntity<BranchResponse> create(@Valid @RequestBody BranchRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(branchService.create(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<BranchResponse> update(@PathVariable long id, @Valid @RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.update(id, request));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BranchResponse> findById(@PathVariable long id) {
        return ResponseEntity.ok(branchService.findById(id));
    }
}
