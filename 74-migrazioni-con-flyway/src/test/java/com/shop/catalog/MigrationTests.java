package com.shop.catalog;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.simple.JdbcClient;

@SpringBootTest
class MigrationTests {
    @Autowired
    JdbcClient jdbc;

    @Test
    void flywayAppliedBothVersions() {
        var versions = jdbc.sql("""
                SELECT "version" FROM "flyway_schema_history"
                WHERE "type" = 'SQL' ORDER BY "installed_rank"
                """).query(String.class).list();
        assertThat(versions).containsExactly("1", "2");
    }
}
