package com.shop.catalog;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class JpaProductRepository implements ProductRepository {
    private final ProductJpaRepository jpa;
    private final CategoryJpaRepository categories;

    public JpaProductRepository(ProductJpaRepository jpa, CategoryJpaRepository categories) {
        this.jpa = jpa;
        this.categories = categories;
    }

    @Override
    public List<Product> findAll() {
        return jpa.findAllWithCategory().stream().map(ProductEntity::toProduct).toList();
    }

    @Override
    public Optional<Product> findById(long id) {
        return jpa.findByIdWithCategory(id).map(ProductEntity::toProduct);
    }

    @Override
    public Product save(Product product) {
        Long id = product.id() == 0 ? null : product.id();
        CategoryEntity category = categories.getReferenceById(product.category().id());
        ProductEntity entity = new ProductEntity(
                id, product.name(), product.description(), product.priceInCents(), category);
        return jpa.save(entity).toProduct();
    }

    @Override
    public boolean existsByName(String name) {
        return jpa.existsByNameIgnoreCase(name);
    }
}
