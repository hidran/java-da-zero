package com.shop.orders;

import java.util.Optional;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Component
public class CatalogProductLookup implements ProductLookup {
    private final CatalogClient catalog;

    public CatalogProductLookup(CatalogClient catalog) {
        this.catalog = catalog;
    }

    @Override
    @Retryable(includes = CatalogUnavailableException.class, maxRetries = 2, delay = 200)
    public Optional<ProductSnapshot> find(long productId) {
        try {
            return Optional.of(catalog.find(productId).toSnapshot());
        } catch (HttpClientErrorException.NotFound exception) {
            return Optional.empty();
        } catch (RestClientException exception) {
            throw new CatalogUnavailableException(exception);
        }
    }
}
