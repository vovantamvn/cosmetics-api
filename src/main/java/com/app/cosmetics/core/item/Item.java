package com.app.cosmetics.core.item;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.comment.Comment;
import com.app.cosmetics.core.lot.Lot;
import com.app.cosmetics.core.orderitem.OrderItem;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Item extends BaseEntity {

    @NotBlank
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    private List<String> images;

    @PositiveOrZero
    private int price;

    @PositiveOrZero
    private int prePrice;

    @PositiveOrZero
    private int discountPrice;

    @PositiveOrZero
    private int sold;

    @ElementCollection
    private List<String> types = new ArrayList<>();

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Lot> lots = new ArrayList<>();
}
