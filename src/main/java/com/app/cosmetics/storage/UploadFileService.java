package com.app.cosmetics.storage;

import org.aspectj.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadFileService {

    private String getStoragePath() {
        return System.getProperty("user.dir") + "/storage";
    }

    public UploadFileResponse saveFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        String type = contentType.substring("image/".length());

        String storagePath = getStoragePath();
        String fileName = UUID.randomUUID() + "." + type;
        String filePath = storagePath + "/" + fileName;

        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(file.getBytes());

        String path = "uploadFile/" + fileName;
        return new UploadFileResponse(path);
    }

    public byte[] loadFile(String fileName) {
        String storagePath = getStoragePath();
        String filePath = storagePath + "/" + fileName;

        File file = new File(filePath);

        try {
            return FileUtil.readAsByteArray(file);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
