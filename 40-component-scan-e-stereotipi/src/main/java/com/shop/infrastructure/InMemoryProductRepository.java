package com.shop.infrastructure;
import com.shop.application.ProductRepository;
import com.shop.domain.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final List<Product> products = new ArrayList<>();
    @Override
    public void save(Product product) {
        products.add(product);
    }
    @Override
    public List<Product> findAll() {
        return List.copyOf(products);
    }
}
