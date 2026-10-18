package com.shop.catalog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class StockService {
    private final Map<Long, Integer> available = new ConcurrentHashMap<>(Map.of(1L, 10, 2L, 5, 3L, 3));

    public synchronized boolean reserve(long productId, int quantity) {
        int stock = available.getOrDefault(productId, 0);
        if (stock < quantity) {
            return false;
        }
        available.put(productId, stock - quantity);
        return true;
    }

    public synchronized void release(long productId, int quantity) {
        available.merge(productId, quantity, Integer::sum);
    }

    public int available(long productId) {
        return available.getOrDefault(productId, 0);
    }
}
