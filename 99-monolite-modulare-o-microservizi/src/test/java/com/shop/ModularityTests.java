package com.shop;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {
    @Test
    void modulesUseOnlyPublicApis() {
        ApplicationModules.of(ShopApplication.class).verify();
    }
}
