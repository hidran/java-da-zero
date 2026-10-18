package com.shop.orders;

import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    private final JdbcClient jdbc;

    public OrderRepository(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public Order save(long productId, String productName, int unitPriceInCents, int quantity) {
        KeyHolder keys = new GeneratedKeyHolder();
        jdbc.sql("""
                insert into orders (product_id, product_name, unit_price_in_cents, quantity)
                values (?, ?, ?, ?)""")
                .params(productId, productName, unitPriceInCents, quantity)
                .update(keys, "id");
        long id = keys.getKey().longValue();
        return new Order(id, productId, productName, unitPriceInCents, quantity);
    }

    public Optional<Order> findById(long id) {
        return jdbc.sql("""
                select id, product_id, product_name, unit_price_in_cents, quantity
                from orders where id = ?""")
                .param(id)
                .query(Order.class)
                .optional();
    }
}
