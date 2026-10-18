package com.shop.orders;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.client.support.OAuth2RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "catalog", types = CatalogClient.class)
public class CatalogClientConfiguration {
    @Bean
    OAuth2RestClientHttpServiceGroupConfigurer serviceToken(OAuth2AuthorizedClientManager clients) {
        return OAuth2RestClientHttpServiceGroupConfigurer.from(clients);
    }
}
