package com.shop.orders;

import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class Outbox {
    private final JdbcClient jdbc;

    public Outbox(JdbcClient jdbc) {
        this.jdbc = jdbc;
    }

    public void add(String topic, String key, String payload) {
        jdbc.sql("""
                insert into outbox (topic, message_key, payload, created_at)
                values (?, ?, ?, current_timestamp)""")
                .params(topic, key, payload)
                .update();
    }

    public List<OutboxMessage> pending(int limit) {
        return jdbc.sql("""
                select id, topic, message_key, payload from outbox
                where sent_at is null order by id limit ?""")
                .param(limit)
                .query(OutboxMessage.class)
                .list();
    }

    public void markSent(long id) {
        jdbc.sql("update outbox set sent_at = current_timestamp where id = ?")
                .param(id)
                .update();
    }
}
