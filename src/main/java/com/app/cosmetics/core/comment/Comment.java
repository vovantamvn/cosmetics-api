package com.app.cosmetics.core.comment;

import com.app.cosmetics.core.base.BaseEntity;
import com.app.cosmetics.core.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class Comment extends BaseEntity {
    @Lob
    private String content;

    @ManyToOne
    private Item item;
}
