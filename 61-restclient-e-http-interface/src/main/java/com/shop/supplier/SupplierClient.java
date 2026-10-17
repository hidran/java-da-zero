package com.shop.supplier;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/supplier")
public interface SupplierClient {
    @GetExchange("/stock/{productId}")
    SupplierStock stock(@PathVariable long productId);
}
