package com.app.cosmetics.core.branch;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Branch extends BaseEntity {
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.REMOVE)
    private List<Item> items = new ArrayList<>();

    public Branch(String name) {
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }
}
