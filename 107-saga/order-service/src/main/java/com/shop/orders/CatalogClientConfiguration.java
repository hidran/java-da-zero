package com.shop.orders;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "catalog", types = CatalogClient.class)
public class CatalogClientConfiguration {
}
