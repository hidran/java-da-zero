package com.shop;
import com.shop.application.CatalogService;
import com.shop.config.ShopConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ShopConfig.class)) {
            CatalogService catalog = context.getBean(CatalogService.class);
            catalog.add("Keyboard", 4990);
            System.out.println("Catalog: " + catalog.products());
            CatalogService again = context.getBean(CatalogService.class);
            System.out.println("Same service: " + (catalog == again));
            Object repository = context.getBean("inMemoryProductRepository");
            System.out.println("Scanned bean: " + repository.getClass().getSimpleName());
        }
    }
}
