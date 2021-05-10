package com.app.cosmetics.core.branch;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Branch extends BaseEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();
}
