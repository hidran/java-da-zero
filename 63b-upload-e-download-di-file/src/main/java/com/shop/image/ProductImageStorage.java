package com.shop.image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ProductImageStorage {
    private final Path root;

    public ProductImageStorage(@Value("${shop.images.dir}") Path root) {
        this.root = root.toAbsolutePath().normalize();
    }

    public void store(long productId, MultipartFile file) throws IOException {
        if (!MediaType.IMAGE_PNG_VALUE.equals(file.getContentType())) {
            throw new InvalidImageException("Only PNG images are accepted.");
        }
        Path folder = root.resolve(String.valueOf(productId));
        Path target = folder.resolve(file.getOriginalFilename()).normalize();
        if (!folder.equals(target.getParent())) {
            throw new InvalidImageException("File name outside the image folder.");
        }
        FileSystemUtils.deleteRecursively(folder);
        Files.createDirectories(folder);
        file.transferTo(target);
    }

    public Optional<Path> find(long productId) throws IOException {
        Path folder = root.resolve(String.valueOf(productId));
        if (!Files.isDirectory(folder)) {
            return Optional.empty();
        }
        try (Stream<Path> files = Files.list(folder)) {
            return files.findFirst();
        }
    }
}
