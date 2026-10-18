package com.shop.orders;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.micrometer.tracing.test.autoconfigure.AutoConfigureTracing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "management.tracing.export.otlp.enabled=false"})
@EmbeddedKafka(partitions = 1, topics = "orders.placed")
@AutoConfigureMockMvc
@AutoConfigureTracing
class TracePropagationTests {
    static final String TRACE_ID = "4bf92f3577b34da6a3ce929d0e0e4736";
    static final String TRACEPARENT = "00-" + TRACE_ID + "-00f067aa0ba902b7-01";
    static final List<String> CATALOG_TRACEPARENTS = new CopyOnWriteArrayList<>();
    static final HttpServer CATALOG = catalogStub();

    @Autowired
    MockMvc mvc;

    @Autowired
    PublishedEvents published;

    @DynamicPropertySource
    static void catalog(DynamicPropertyRegistry registry) {
        registry.add("spring.http.serviceclient.catalog.base-url",
                () -> "http://127.0.0.1:" + CATALOG.getAddress().getPort());
    }

    @AfterAll
    static void stopStub() {
        CATALOG.stop(0);
    }

    @Test
    void theCatalogCallCarriesTheSameTraceId() throws Exception {
        placeOrder();
        assertThat(CATALOG_TRACEPARENTS).isNotEmpty().allSatisfy(header -> assertThat(header).contains(TRACE_ID));
    }

    @Test
    void theOrderPlacedEventCarriesTheSameTraceId() throws Exception {
        placeOrder();
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(published.traceparents).anySatisfy(header -> assertThat(header).contains(TRACE_ID)));
    }

    private void placeOrder() throws Exception {
        mvc.perform(post("/api/orders").header("traceparent", TRACEPARENT)
                        .contentType(MediaType.APPLICATION_JSON).content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isCreated());
    }

    private static HttpServer catalogStub() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
            server.createContext("/api/products/1", exchange -> {
                CATALOG_TRACEPARENTS.add(String.valueOf(exchange.getRequestHeaders().getFirst("traceparent")));
                byte[] body = "{\"id\":1,\"name\":\"Keyboard\",\"priceInCents\":4900}".getBytes(StandardCharsets.UTF_8);
                exchange.getResponseHeaders().add("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, body.length);
                exchange.getResponseBody().write(body);
                exchange.close();
            });
            server.start();
            return server;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @TestConfiguration
    static class PublishedEvents {
        final List<String> traceparents = new CopyOnWriteArrayList<>();

        @KafkaListener(topics = "orders.placed", groupId = "orders-test")
        void capture(ConsumerRecord<String, String> record) {
            Header header = record.headers().lastHeader("traceparent");
            traceparents.add(header == null ? "none" : new String(header.value(), StandardCharsets.UTF_8));
        }
    }
}
