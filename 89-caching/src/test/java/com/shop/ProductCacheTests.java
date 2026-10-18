package com.shop;

import com.shop.catalog.Product;
import com.shop.catalog.ProductNotFoundException;
import com.shop.catalog.ProductRepository;
import com.shop.catalog.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class ProductCacheTests {
    @Autowired ProductService service;
    @Autowired CacheManager cacheManager;
    @MockitoSpyBean ProductRepository repository;

    @BeforeEach
    void emptyTheCache() {
        cacheManager.getCache("products").clear();
    }

    @Test
    void secondReadComesFromTheCache() {
        service.find(1);
        service.find(1);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void statisticsCountTheHit() {
        CaffeineCache cache = (CaffeineCache) cacheManager.getCache("products");
        long hits = cache.getNativeCache().stats().hitCount();
        service.find(2);
        service.find(2);
        assertThat(cache.getNativeCache().stats().hitCount()).isEqualTo(hits + 1);
    }

    @Test
    void updateReplacesTheCachedProduct() {
        service.find(3);
        service.update(3, "Curved monitor", 21900);
        assertThat(service.find(3).name()).isEqualTo("Curved monitor");
    }

    @Test
    void deleteEvictsTheCachedProduct() {
        Product webcam = service.create("Webcam", 3900);
        service.find(webcam.id());
        service.delete(webcam.id());
        assertThatThrownBy(() -> service.find(webcam.id()))
                .isInstanceOf(ProductNotFoundException.class);
    }
}
