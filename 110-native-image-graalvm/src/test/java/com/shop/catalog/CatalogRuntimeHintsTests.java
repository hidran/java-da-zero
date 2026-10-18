package com.shop.catalog;

import org.junit.jupiter.api.Test;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.predicate.RuntimeHintsPredicates;
import static org.assertj.core.api.Assertions.assertThat;

class CatalogRuntimeHintsTests {
    @Test
    void demoCatalogIsRegisteredForTheNativeImage() {
        RuntimeHints hints = new RuntimeHints();
        new CatalogRuntimeHints().registerHints(hints, getClass().getClassLoader());
        assertThat(RuntimeHintsPredicates.resource().forResource("catalog/demo-products.csv")).accepts(hints);
    }
}
