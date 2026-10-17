package com.shop.catalog;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductImporter {
    private final ProductService productService;

    public ProductImporter(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> importAll(List<ProductDraft> drafts) {
        return drafts.stream()
                .map(draft -> productService.create(draft.name(), draft.priceInCents()))
                .toList();
    }
}
