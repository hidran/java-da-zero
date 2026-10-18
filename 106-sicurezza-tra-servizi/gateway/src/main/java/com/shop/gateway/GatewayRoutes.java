package com.shop.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.web.servlet.function.RequestPredicates.path;

@Configuration
public class GatewayRoutes {
    @Bean
    RouterFunction<ServerResponse> catalogRoute(@Value("${shop.catalog-url}") String catalogUrl) {
        return route("catalog").route(path("/api/products/**"), http()).before(uri(catalogUrl))
                .filter(RequestIdFilter.requestId()).build();
    }
    @Bean
    RouterFunction<ServerResponse> ordersRoute(@Value("${shop.orders-url}") String ordersUrl) {
        return route("orders").route(path("/api/orders/**"), http()).before(uri(ordersUrl))
                .filter(RequestIdFilter.requestId()).build();
    }
    @Bean
    RouterFunction<ServerResponse> keycloakRoute(@Value("${shop.keycloak-url}") String keycloakUrl) {
        return route("keycloak").route(path("/realms/**"), http()).before(uri(keycloakUrl)).build();
    }
}
