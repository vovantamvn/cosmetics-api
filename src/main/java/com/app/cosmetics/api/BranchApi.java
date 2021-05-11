package com.app.cosmetics.api;

import com.app.cosmetics.api.exception.InvalidRequestException;
import com.app.cosmetics.application.AuthorizationService;
import com.app.cosmetics.application.data.BranchData;
import com.app.cosmetics.application.BranchService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "branches")
public class BranchApi {

    private final BranchService branchService;
    private final AuthorizationService authorizationService;

    @GetMapping
    public ResponseEntity<List<BranchData>> findAll() {
        return ResponseEntity.ok(branchService.findAll());
    }

    @PostMapping
    public ResponseEntity<BranchData> create(
            @Valid @RequestBody BranchRequest request,
            BindingResult bindingResult,
            @AuthenticationPrincipal User user
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        System.out.println("Authorization");
        System.out.println(user);
        System.out.println(authorizationService.isAdmin(user));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(branchService.create(request.getName()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<BranchData> update(
            @PathVariable long id,
            @Valid @RequestBody BranchRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(bindingResult);
        }

        return ResponseEntity.ok(branchService.update(id, request.getName()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BranchData> findById(@PathVariable long id) {
        return ResponseEntity.ok(branchService.findById(id));
    }
}

@Setter
@Getter
@NoArgsConstructor
class BranchRequest {
    @NotBlank(message = "can't blank")
    private String name;
}
