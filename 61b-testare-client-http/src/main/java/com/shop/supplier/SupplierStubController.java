package com.shop.supplier;

import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supplier")
public class SupplierStubController {
    private static final Map<Long, Integer> STOCK = Map.of(1L, 12, 2L, 0, 3L, 4);

    @GetMapping("/stock/{productId}")
    public SupplierStock stock(@PathVariable long productId) {
        return new SupplierStock(productId, STOCK.getOrDefault(productId, 0));
    }
}
