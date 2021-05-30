package com.app.cosmetics.application.data;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentData {
    private Long id;
    private Long itemId;
    private String content;
}
