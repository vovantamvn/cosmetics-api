package com.app.cosmetics.storage;

import com.app.cosmetics.application.data.FileData;
import com.app.cosmetics.application.UploadFileService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class UploadFileServiceTest {

    private UploadFileService uploadFileService = new UploadFileService();

    @Test
    void it_should_load_file() throws IOException {
        // Arrange
        String currentPath = System.getProperty("user.dir");
        String fileName = "image.png";
        String filePath = currentPath + "/storage/" + fileName;

        Path path = Paths.get(filePath);
        byte[] expected = {1, 2, 3, 4, 5, 6};
        Files.write(path, expected);

        // Act
        byte[] actual = uploadFileService.loadFile(fileName);

        // Assert
        assertArrayEquals(expected, actual);
    }

    @Test
    void it_should_throw_error() {
        // Arrange
        String fileName = "123456.png.img";

        // Act

        // Assert
        assertThrows(RuntimeException.class, () -> uploadFileService.loadFile(fileName));
    }

    @Test
    void it_should_save_file_to_storage() throws IOException {
        System.out.println("1");
        // Arrange
        byte[] content = {1, 0, 0, 0, 1};
        MockMultipartFile multipartFile = new MockMultipartFile(
                "file",
                "originalFileName.png",
                MediaType.IMAGE_PNG_VALUE,
                content
        );

        // Act
        FileData response = uploadFileService.saveFile(multipartFile);
        String path = response.getPath(); // path: {uploadFile/fileName}
        String fileName = path.substring("uploadFile/".length());

        String currentPath = System.getProperty("user.dir");
        String filePath = currentPath + "/storage/" + fileName;
        System.out.println(filePath);

        File file = new File(filePath);
        byte[] actual = FileUtil.readAsByteArray(file);

        // Assert
        assertArrayEquals(content, actual);
    }
}