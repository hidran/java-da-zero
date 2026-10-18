package com.shop.jobs;

import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InventoryJobs {
    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryJobs.class);
    private final AtomicInteger stockChecks = new AtomicInteger();

    @Scheduled(fixedDelayString = "${shop.jobs.stock-check-delay}")
    public void checkStock() {
        int run = stockChecks.incrementAndGet();
        LOGGER.info("Stock check {} on {}", run, Thread.currentThread());
    }

    @Scheduled(cron = "${shop.jobs.cleanup-cron}")
    public void cleanUpExpiredCarts() {
        LOGGER.info("Cleaning up expired carts on {}", Thread.currentThread());
    }

    public int stockChecks() {
        return stockChecks.get();
    }
}
