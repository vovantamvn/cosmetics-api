package com.app.cosmetics.api;

import com.app.cosmetics.application.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "uploadFile/")
@RequiredArgsConstructor
public class UploadFileController {

    private final UploadFileService service;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveFile(@RequestBody MultipartFile file) throws IOException {
        if (file.getContentType() != null && file.getContentType().startsWith("image")) {
            return ResponseEntity.ok(service.saveFile(file));
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Content type must be image");
    }

    @GetMapping(value = "/{fileName}")
    public ResponseEntity<?> loadFile(@PathVariable String fileName) {
        byte[] bytes = service.loadFile(fileName);

        return ResponseEntity
                .ok()
                .contentLength(bytes.length)
                .contentType(MediaType.IMAGE_JPEG)
                .body(bytes);
    }
}
