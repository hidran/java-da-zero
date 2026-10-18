package com.shop.orders;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogClient {
    @GetExchange("/api/products/{id}")
    ProductView product(@PathVariable long id);
}
