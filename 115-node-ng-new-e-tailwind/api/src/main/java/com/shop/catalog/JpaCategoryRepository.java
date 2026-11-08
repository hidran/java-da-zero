package com.shop.catalog;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class JpaCategoryRepository implements CategoryRepository {
    private final CategoryJpaRepository jpa;

    public JpaCategoryRepository(CategoryJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public List<Category> findAll() {
        return jpa.findAll(Sort.by("name")).stream().map(CategoryEntity::toCategory).toList();
    }

    @Override
    public Optional<Category> findById(long id) {
        return jpa.findById(id).map(CategoryEntity::toCategory);
    }
}
