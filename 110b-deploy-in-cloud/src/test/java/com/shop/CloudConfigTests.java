package com.shop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "PORT=9090")
@ActiveProfiles("cloud")
class CloudConfigTests {
    @Autowired
    Environment environment;

    @Test
    void listensOnThePortChosenByThePlatform() {
        assertThat(environment.getProperty("server.port")).isEqualTo("9090");
    }

    @Test
    void listensOnAllInterfacesOfTheContainer() {
        assertThat(environment.getProperty("server.address")).isEqualTo("0.0.0.0");
    }
}
