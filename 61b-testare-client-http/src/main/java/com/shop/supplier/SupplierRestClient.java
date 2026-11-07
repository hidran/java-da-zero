package com.shop.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SupplierRestClient {
    private final RestClient restClient;

    public SupplierRestClient(RestClient.Builder builder,
            @Value("${spring.http.serviceclient.supplier.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public SupplierStock stock(long productId) {
        return restClient.get()
                .uri("/supplier/stock/{productId}", productId)
                .retrieve()
                .body(SupplierStock.class);
    }
}
