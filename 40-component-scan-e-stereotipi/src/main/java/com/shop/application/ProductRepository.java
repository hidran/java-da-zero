package com.shop.application;
import com.shop.domain.Product;
import java.util.List;
public interface ProductRepository {
    void save(Product product);
    List<Product> findAll();
}
