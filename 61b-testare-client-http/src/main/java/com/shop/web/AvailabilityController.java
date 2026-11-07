package com.shop.web;

import com.shop.catalog.Product;
import com.shop.catalog.ProductService;
import com.shop.supplier.SupplierClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AvailabilityController {
    private final ProductService products;
    private final SupplierClient supplier;

    public AvailabilityController(ProductService products, SupplierClient supplier) {
        this.products = products;
        this.supplier = supplier;
    }

    @GetMapping("/api/products/{id}/availability")
    public AvailabilityResponse availability(@PathVariable long id) {
        Product product = products.find(id);
        int available = supplier.stock(id).available();
        return new AvailabilityResponse(product.id(), product.name(), available);
    }
}
