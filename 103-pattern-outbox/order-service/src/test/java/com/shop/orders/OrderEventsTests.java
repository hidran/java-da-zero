package com.shop.orders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka(topics = "orders.placed")
class OrderEventsTests {
    @Autowired
    OrderService orders;

    @Autowired
    OutboxRelay relay;

    @Autowired
    EmbeddedKafkaBroker broker;

    @Autowired
    JsonMapper json;

    @MockitoBean
    ProductLookup products;

    @Test
    void theRelayPublishesOrderPlacedAsJson() {
        given(products.find(1L)).willReturn(Optional.of(new ProductSnapshot(1L, "Keyboard", 4900)));
        Order order = orders.place(1L, 2);
        relay.publishPending();
        Map<String, Object> props = KafkaTestUtils.consumerProps(broker, "test", true);
        props.put("auto.offset.reset", "earliest");
        try (Consumer<String, String> consumer = new DefaultKafkaConsumerFactory<>(props,
                new StringDeserializer(), new StringDeserializer()).createConsumer()) {
            broker.consumeFromAnEmbeddedTopic(consumer, "orders.placed");
            ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, "orders.placed",
                    Duration.ofSeconds(10));
            JsonNode event = json.readTree(record.value());
            assertThat(record.key()).isEqualTo(String.valueOf(order.id()));
            assertThat(event.get("orderId").asLong()).isEqualTo(order.id());
            assertThat(event.get("productId").asLong()).isEqualTo(1L);
            assertThat(event.get("quantity").asInt()).isEqualTo(2);
            assertThat(event.get("totalInCents").asInt()).isEqualTo(9800);
        }
    }
}
