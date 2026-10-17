package com.shop.catalog;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaProductRepository implements ProductRepository {
    private final ProductJpaRepository jpa;
    public JpaProductRepository(ProductJpaRepository jpa) {
        this.jpa = jpa;
    }
    @Override
    public List<Product> findAll() {
        return jpa.findAll(Sort.by("id")).stream().map(ProductEntity::toProduct).toList();
    }
    @Override
    public Optional<Product> findById(long id) {
        return jpa.findById(id).map(ProductEntity::toProduct);
    }
    @Override
    public Product save(Product product) {
        return jpa.save(ProductEntity.from(product)).toProduct();
    }
    @Override
    public boolean deleteById(long id) {
        if (!jpa.existsById(id)) {
            return false;
        }
        jpa.deleteById(id);
        return true;
    }
}
