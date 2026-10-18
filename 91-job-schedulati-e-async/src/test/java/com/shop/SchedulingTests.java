package com.shop;

import com.shop.jobs.InventoryJobs;
import com.shop.order.Confirmation;
import com.shop.order.ConfirmationService;
import java.time.Duration;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.support.CronExpression;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest(properties = "shop.jobs.stock-check-delay=100ms")
class SchedulingTests {
    @Autowired ConfirmationService confirmations;
    @Autowired InventoryJobs jobs;
    @Value("${shop.jobs.cleanup-cron}") String cleanupCron;

    @Test
    void confirmationRunsOnAnotherThread() {
        Confirmation confirmation = confirmations.send(42).join();
        assertThat(confirmation.thread()).isNotEqualTo(Thread.currentThread().getName());
    }

    @Test
    void confirmationRunsOnAVirtualThread() {
        assertThat(confirmations.send(42).join().virtual()).isTrue();
    }

    @Test
    void stockCheckRunsAgainAndAgain() {
        await().atMost(Duration.ofSeconds(2)).until(() -> jobs.stockChecks() >= 2);
    }

    @Test
    void cleanupRunsAtTwoAtNight() {
        LocalDateTime noon = LocalDateTime.of(2026, 10, 16, 12, 0);
        LocalDateTime next = CronExpression.parse(cleanupCron).next(noon);
        assertThat(next).isEqualTo(LocalDateTime.of(2026, 10, 17, 2, 0));
    }
}
