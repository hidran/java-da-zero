package com.shop.catalog;

import java.util.Optional;

public interface ProductCatalog {
    Optional<Product> find(long id);
}
