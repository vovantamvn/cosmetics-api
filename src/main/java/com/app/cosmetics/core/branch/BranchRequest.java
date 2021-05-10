package com.app.cosmetics.core.branch;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class BranchRequest {
    @NotBlank
    private String name;
}
