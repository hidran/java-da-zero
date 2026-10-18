package com.shop.stock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.resilience.annotation.ConcurrencyLimit;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Component
public class SupplierClient {
    private final RestClient restClient;

    public SupplierClient(RestClient.Builder builder,
            @Value("${shop.supplier.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @Retryable(includes = HttpServerErrorException.class, maxRetries = 2, delay = 200)
    @ConcurrencyLimit(2)
    public StockLevel stock(long productId) {
        return restClient.get().uri("/stock/{id}", productId)
                .retrieve().body(StockLevel.class);
    }
}
