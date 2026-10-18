package com.shop.config;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.cloud.config.server.native.search-locations=file:../config-repo")
class ConfigServerTests {
    @LocalServerPort
    int port;

    @Test
    void everyServiceGetsTheSharedSettings() throws Exception {
        String body = get("/catalog-service/default");
        assertThat(body).contains("\"eureka.client.service-url.defaultZone\":\"http://127.0.0.1:21055/eureka/\"");
        assertThat(body).contains("\"logging.level.root\":\"warn\"");
    }

    @Test
    void orderServiceFindsTheCatalogByName() throws Exception {
        String body = get("/order-service/default");
        assertThat(body).contains("\"spring.http.serviceclient.catalog.base-url\":\"lb://catalog-service\"");
        assertThat(body).contains("\"eureka.client.service-url.defaultZone\"");
    }

    private String get(String path) throws Exception {
        HttpRequest request = HttpRequest.newBuilder(URI.create("http://127.0.0.1:" + port + path)).build();
        return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
