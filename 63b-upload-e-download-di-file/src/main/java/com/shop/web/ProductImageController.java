package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import com.shop.image.ProductImageStorage;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/products/{id}/image")
public class ProductImageController {
    private final ProductService products;
    private final ProductImageStorage images;

    public ProductImageController(ProductService products, ProductImageStorage images) {
        this.products = products;
        this.images = images;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> upload(@PathVariable long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        Product product = products.find(id);
        images.store(product.id(), file);
        URI location = URI.create("/api/products/" + id + "/image");
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<Resource> download(@PathVariable long id) throws IOException {
        Optional<Path> image = images.find(id);
        if (image.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ContentDisposition disposition = ContentDisposition.inline()
                .filename(image.get().getFileName().toString()).build();
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .body(new FileSystemResource(image.get()));
    }
}
