package com.shop.catalog;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcProductRepository implements ProductRepository {
    private final JdbcClient jdbc;

    public JdbcProductRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Product> findAll() {
        return jdbc.sql("SELECT id, name, price_in_cents FROM product ORDER BY id")
                .query(JdbcProductRepository::toProduct)
                .list();
    }

    @Override
    public Optional<Product> findById(long id) {
        return jdbc.sql("SELECT id, name, price_in_cents FROM product WHERE id = :id")
                .param("id", id)
                .query(JdbcProductRepository::toProduct)
                .optional();
    }

    @Override
    public Product save(Product product) {
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.sql("INSERT INTO product (name, price_in_cents) VALUES (:name, :price)")
                .param("name", product.name())
                .param("price", product.priceInCents())
                .update(keys, "id");
        long id = keys.getKeyAs(Long.class);
        return new Product(id, product.name(), product.priceInCents());
    }

    @Override
    public boolean deleteById(long id) {
        return jdbc.sql("DELETE FROM product WHERE id = :id").param("id", id).update() == 1;
    }

    private static Product toProduct(ResultSet row, int rowNumber) throws SQLException {
        return new Product(row.getLong("id"), row.getString("name"), row.getInt("price_in_cents"));
    }
}
