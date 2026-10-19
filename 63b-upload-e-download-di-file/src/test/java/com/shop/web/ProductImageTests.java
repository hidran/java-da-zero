package com.shop.web;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductImageTests {
    static final byte[] PNG = {(byte) 0x89, 'P', 'N', 'G', 13, 10, 26, 10};
    @TempDir static Path tempDir;
    @Autowired MockMvc mvc;

    @DynamicPropertySource
    static void imagesFolder(DynamicPropertyRegistry registry) {
        registry.add("shop.images.dir", () -> tempDir.resolve("images").toString());
    }

    @Test
    void uploadedImageCanBeDownloaded() throws Exception {
        var image = new MockMultipartFile("file", "keyboard.png", "image/png", PNG);
        mvc.perform(multipart("/api/products/1/image").file(image))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/products/1/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("image/png"))
                .andExpect(header().string("Content-Disposition",
                        "inline; filename=\"keyboard.png\""))
                .andExpect(content().bytes(PNG));
    }

    @Test
    void fileNameCannotLeaveTheImageFolder() throws Exception {
        var image = new MockMultipartFile("file", "../../escaped.png", "image/png", PNG);
        var upload = mvc.perform(multipart("/api/products/1/image").file(image));
        assertThat(tempDir.resolve("escaped.png")).doesNotExist();
        upload.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid image"));
    }

    @Test
    void onlyPngImagesAreAccepted() throws Exception {
        var notes = new MockMultipartFile("file", "notes.txt", "text/plain", new byte[1]);
        mvc.perform(multipart("/api/products/1/image").file(notes))
                .andExpect(status().isBadRequest());
    }
}
