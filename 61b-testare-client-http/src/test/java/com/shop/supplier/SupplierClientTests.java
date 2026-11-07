package com.shop.supplier;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.http.client.autoconfigure.service.HttpServiceClientPropertiesAutoConfiguration;
import org.springframework.boot.restclient.autoconfigure.service.HttpServiceClientAutoConfiguration;
import org.springframework.boot.restclient.test.autoconfigure.RestClientTest;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RestClientTest(
        properties = "spring.http.serviceclient.supplier.base-url=http://supplier.test")
@ImportAutoConfiguration({HttpServiceClientPropertiesAutoConfiguration.class,
        HttpServiceClientAutoConfiguration.class})
class SupplierClientTests {
    @Autowired SupplierClient client;
    @Autowired MockRestServiceServer server;

    @Test
    void readsTheStockFromTheSupplier() {
        String json = "{\"productId\":1,\"available\":12}";
        server.expect(requestTo("http://supplier.test/supplier/stock/1"))
                .andExpect(method(GET))
                .andRespond(withSuccess(json, APPLICATION_JSON));
        assertThat(client.stock(1)).isEqualTo(new SupplierStock(1, 12));
        server.verify();
    }

    @Test
    void serverErrorBecomesAnException() {
        server.expect(requestTo("http://supplier.test/supplier/stock/1"))
                .andRespond(withServerError());
        assertThatThrownBy(() -> client.stock(1))
                .isInstanceOf(HttpServerErrorException.class);
    }

    @Test
    void unknownProductIsNotFound() {
        server.expect(requestTo("http://supplier.test/supplier/stock/99"))
                .andRespond(withStatus(NOT_FOUND));
        assertThatThrownBy(() -> client.stock(99))
                .isInstanceOf(HttpClientErrorException.NotFound.class);
    }
}
