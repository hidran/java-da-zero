package com.shop.notifications;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJacksonJsonMessageConverter;

@Configuration
public class KafkaConfig {
    @Bean
    RecordMessageConverter jsonEvents() {
        return new StringJacksonJsonMessageConverter();
    }
}
