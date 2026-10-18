package com.shop.orders;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    NewTopic ordersPlaced() {
        return TopicBuilder.name("orders.placed").partitions(1).replicas(1).build();
    }
}
