package com.shop.web;

import com.shop.stock.StockLevel;
import com.shop.stock.SupplierClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    private final SupplierClient supplier;

    public StockController(SupplierClient supplier) {
        this.supplier = supplier;
    }

    @GetMapping("/api/products/{id}/stock")
    public StockLevel stock(@PathVariable long id) {
        return supplier.stock(id);
    }
}
