package com.app.cosmetics.core.item;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.branch.Branch;
import com.app.cosmetics.core.category.Category;
import com.app.cosmetics.core.comment.Comment;
import com.app.cosmetics.core.orderitem.OrderItem;
import com.app.cosmetics.core.stock.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Item extends BaseEntity {

    @NotBlank
    private String name;

    @Lob
    private String description;

    private String image;
    @PositiveOrZero

    private int count;

    @PositiveOrZero
    private int price;

    @PositiveOrZero
    private int prePrice;

    private LocalDate expiry;

    @OneToMany(mappedBy = "item")
    private List<Stock> stocks = new ArrayList<>();

    @ManyToOne
    private Branch branch;

    @ManyToOne
    private Category category;
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany
    private List<Comment> comments = new ArrayList<>();

    public Item(String name, String description, String image, int count, int price, List<Stock> stocks, Branch branch, Category category) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.count = count;
        this.price = price;
        this.stocks = stocks;
        this.branch = branch;
        this.category = category;
    }

    public void update(String name, String description, String image, int count, int price, Branch branch, Category category) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.count = count;
        this.price = price;
        this.branch = branch;
        this.category = category;
    }
}
