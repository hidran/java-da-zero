package com.shop.web;

import com.shop.order.Confirmation;
import com.shop.order.ConfirmationService;
import java.util.concurrent.CompletableFuture;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfirmationController {
    private final ConfirmationService confirmations;

    public ConfirmationController(ConfirmationService confirmations) {
        this.confirmations = confirmations;
    }

    @PostMapping("/api/orders/{id}/confirmation")
    public CompletableFuture<Confirmation> confirm(@PathVariable long id) {
        return confirmations.send(id);
    }
}
