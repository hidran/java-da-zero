package com.shop.orders;

import org.springframework.security.oauth2.client.annotation.ClientRegistrationId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

public interface CatalogClient {
    @ClientRegistrationId("catalog-client")
    @GetExchange("/api/products/{id}")
    ProductView product(@PathVariable long id);
}
