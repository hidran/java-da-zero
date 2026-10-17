package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import com.shop.catalog.ProductSort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ProductPageResponse list(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "ID") ProductSort sort,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(20) int size) {
        return ProductPageResponse.from(service.search(name, sort, page, size));
    }

    @GetMapping("/{id}")
    public ProductResponse find(@PathVariable long id) {
        return ProductResponse.from(service.find(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        Product product = service.create(request.name(), request.priceInCents());
        URI location = URI.create("/api/products/" + product.id());
        return ResponseEntity.created(location).body(ProductResponse.from(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
