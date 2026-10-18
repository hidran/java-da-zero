package com.shop.orders;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/products")
public interface CatalogClient {
    @GetExchange("/{id}")
    CatalogProduct find(@PathVariable long id);
}
