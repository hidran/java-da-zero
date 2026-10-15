package com.shop;
import com.shop.application.OrderRepository;
import com.shop.application.PlaceOrderService;
import com.shop.infrastructure.InMemoryOrderRepository;
public class Main {
    public static void main(String[] args) {
        OrderRepository repository = new InMemoryOrderRepository();
        PlaceOrderService service = new PlaceOrderService(repository);
        System.out.println("Placed: " + service.place("A-1", 2500));
        try {
            service.place("A-2", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejected: " + e.getMessage());
        }
        System.out.println("Stored: " + repository.findAll());
    }
}
