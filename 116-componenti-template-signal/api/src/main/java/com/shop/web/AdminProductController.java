package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Admin", description = "Change the catalog (role ADMIN)")
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {
    private final ProductService service;

    public AdminProductController(ProductService service) {
        this.service = service;
    }

    @Operation(summary = "Create a product")
    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        Product product = service.create(
                request.name(), request.description(), request.priceInCents(), request.categoryId());
        URI location = URI.create("/api/products/" + product.id());
        return ResponseEntity.created(location).body(ProductResponse.from(product));
    }
}
