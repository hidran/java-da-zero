package com.shop.orders;

import java.util.Optional;

public interface ProductLookup {
    Optional<ProductSnapshot> find(long productId);
}
