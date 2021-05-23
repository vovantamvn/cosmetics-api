package com.app.cosmetics.application.data;

import lombok.Getter;

@Getter
public class FileData {
    private final String path;

    public FileData(String path) {
        this.path = path;
    }
}
