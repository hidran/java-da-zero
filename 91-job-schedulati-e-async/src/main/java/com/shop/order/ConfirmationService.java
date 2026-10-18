package com.shop.order;

import java.util.concurrent.CompletableFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ConfirmationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationService.class);

    @Async
    public CompletableFuture<Confirmation> send(long orderId) {
        Thread thread = Thread.currentThread();
        LOGGER.info("Sending confirmation for order {}", orderId);
        return CompletableFuture.completedFuture(
                new Confirmation(orderId, thread.getName(), thread.isVirtual()));
    }
}
