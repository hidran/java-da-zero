package com.shop.orders;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJacksonJsonMessageConverter;

@Configuration
public class KafkaConfig {
    @Bean
    RecordMessageConverter jsonEvents() {
        return new StringJacksonJsonMessageConverter();
    }

    @Bean
    NewTopic ordersPlaced() {
        return TopicBuilder.name("orders.placed").partitions(1).replicas(1).build();
    }

    @Bean
    NewTopic ordersCancelled() {
        return TopicBuilder.name("orders.cancelled").partitions(1).replicas(1).build();
    }
}
