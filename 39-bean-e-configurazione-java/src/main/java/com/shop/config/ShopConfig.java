package com.shop.config;
import com.shop.application.CatalogService;
import com.shop.application.ProductRepository;
import com.shop.infrastructure.InMemoryProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ShopConfig {
    @Bean
    public ProductRepository productRepository() {
        return new InMemoryProductRepository();
    }
    @Bean
    public CatalogService catalogService(ProductRepository productRepository) {
        return new CatalogService(productRepository);
    }
}
