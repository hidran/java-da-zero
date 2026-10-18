package com.shop.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class DatabaseUsersTests {
    private static final String NEW_PRODUCT = "{\"name\":\"Webcam\",\"priceInCents\":5900}";

    @Autowired
    MockMvc mvc;

    @Autowired
    JdbcClient jdbc;

    @Autowired
    PasswordEncoder encoder;

    @Test
    void aliceStillCreatesAProduct() throws Exception {
        create("alice", "alice-demo").andExpect(status().isCreated());
    }

    @Test
    void carolExistsOnlyInTheDatabase() throws Exception {
        create("carol", "carol-demo").andExpect(status().isCreated());
    }

    @Test
    void disabledUserIsRejected() throws Exception {
        create("dave", "dave-demo").andExpect(status().isUnauthorized());
    }

    @Test
    void userInsertedAtRuntimeCanLogIn() throws Exception {
        jdbc.sql("insert into app_user values (?, ?, 'USER', true)")
                .params("erin", encoder.encode("erin-demo"))
                .update();
        create("erin", "erin-demo").andExpect(status().isCreated());
    }

    @Test
    void roleComesFromTheDatabase() throws Exception {
        mvc.perform(delete("/api/products/3").header(HttpHeaders.AUTHORIZATION, basic("bob", "bob-demo")))
                .andExpect(status().isNoContent());
        mvc.perform(delete("/api/products/2").header(HttpHeaders.AUTHORIZATION, basic("carol", "carol-demo")))
                .andExpect(status().isForbidden());
    }

    @Test
    void databaseStoresOnlyHashes() {
        var hashes = jdbc.sql("select password_hash from app_user").query(String.class).list();
        assertThat(hashes).isNotEmpty().allMatch(hash -> hash.startsWith("{bcrypt}"));
    }

    private ResultActions create(String user, String password) throws Exception {
        return mvc.perform(post("/api/products").header(HttpHeaders.AUTHORIZATION, basic(user, password))
                .contentType(MediaType.APPLICATION_JSON).content(NEW_PRODUCT));
    }

    private static String basic(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));
    }
}
