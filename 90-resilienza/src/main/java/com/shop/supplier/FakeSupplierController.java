package com.shop.supplier;

import com.shop.stock.StockLevel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FakeSupplierController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FakeSupplierController.class);
    private final Map<Long, AtomicInteger> calls = new ConcurrentHashMap<>();

    @GetMapping("/supplier/stock/{productId}")
    public ResponseEntity<StockLevel> stock(@PathVariable long productId)
            throws InterruptedException {
        int call = calls.computeIfAbsent(productId, id -> new AtomicInteger()).incrementAndGet();
        if (productId == 2) {
            LOGGER.info("Supplier is slow for product {}", productId);
            Thread.sleep(3000);
        }
        if (productId == 4 || (productId == 3 && call % 3 != 0)) {
            LOGGER.info("Supplier fails for product {}, call {}", productId, call);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
        LOGGER.info("Supplier answers for product {}, call {}", productId, call);
        return ResponseEntity.ok(new StockLevel(productId, 12));
    }

    public int calls(long productId) {
        return calls.getOrDefault(productId, new AtomicInteger()).get();
    }
}
