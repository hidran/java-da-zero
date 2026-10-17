package com.shop.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class PasswordStorageTests {
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDetailsService users;

    @Test
    void sameDemoPasswordGivesDifferentSaltedHashes() {
        String first = encoder.encode("bob-demo");
        String second = encoder.encode("bob-demo");
        assertThat(first).startsWith("{bcrypt}").isNotEqualTo(second);
        assertThat(encoder.matches("bob-demo", first)).isTrue();
        assertThat(encoder.matches("bob-demo", second)).isTrue();
    }

    @Test
    void storedPasswordIsOnlyAHash() {
        String stored = users.loadUserByUsername("bob").getPassword();
        assertThat(stored).startsWith("{bcrypt}").doesNotContain("bob-demo");
        assertThat(encoder.matches("bob-demo", stored)).isTrue();
        assertThat(encoder.matches("wrong-password", stored)).isFalse();
    }
}
