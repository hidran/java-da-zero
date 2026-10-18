package com.shop.web;

import com.shop.catalog.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClearanceController {
    private final ProductService service;

    public ClearanceController(ProductService service) {
        this.service = service;
    }

    // Added later by another team: removes a product sold out in a clearance sale
    @PostMapping("/api/clearance/{id}")
    public ResponseEntity<Void> clear(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
