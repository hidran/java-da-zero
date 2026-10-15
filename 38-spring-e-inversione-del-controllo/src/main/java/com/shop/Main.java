package com.shop;
import com.shop.catalog.CatalogService;
import com.shop.catalog.InMemoryProductRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class Main {
    public static void main(String[] args) {
        InMemoryProductRepository repository = new InMemoryProductRepository();
        CatalogService service = new CatalogService(repository);
        System.out.println("By hand: " + service.totalInCents("pen", 3));
        try (var context = new AnnotationConfigApplicationContext(
                InMemoryProductRepository.class, CatalogService.class)) {
            CatalogService managed = context.getBean(CatalogService.class);
            System.out.println("With Spring: " + managed.totalInCents("pen", 3));
        }
    }
}
