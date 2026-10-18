package com.shop.notifications;

import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.micrometer.tracing.test.autoconfigure.AutoConfigureTracing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}",
        "management.tracing.export.otlp.enabled=false",
        "management.opentelemetry.tracing.export.schedule-delay=100ms"})
@EmbeddedKafka(partitions = 1, topics = "orders.placed")
@AutoConfigureTracing
class NotificationTracingTests {
    static final String TRACE_ID = "4bf92f3577b34da6a3ce929d0e0e4736";

    @Autowired
    EmbeddedKafkaBroker broker;

    @Autowired
    InMemorySpanExporter spans;

    @Test
    void theListenerContinuesTheTraceOfTheEvent() {
        ProducerRecord<String, String> record = new ProducerRecord<>("orders.placed", "1",
                "{\"orderId\":1,\"productId\":1,\"quantity\":2,\"totalInCents\":9800}");
        record.headers().add("traceparent",
                ("00-" + TRACE_ID + "-00f067aa0ba902b7-01").getBytes(StandardCharsets.UTF_8));
        new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(KafkaTestUtils.producerProps(broker),
                new StringSerializer(), new StringSerializer())).send(record);
        await().atMost(Duration.ofSeconds(10)).untilAsserted(() ->
                assertThat(spans.getFinishedSpanItems()).anySatisfy(span ->
                        assertThat(span.getTraceId()).isEqualTo(TRACE_ID)));
    }

    @TestConfiguration
    static class Spans {
        @Bean
        InMemorySpanExporter inMemorySpanExporter() {
            return InMemorySpanExporter.create();
        }
    }
}
