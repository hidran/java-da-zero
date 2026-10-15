package com.shop.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
@Configuration
@ComponentScan("com.shop")
@PropertySource("classpath:shop.properties")
public class ShopConfig {
}
