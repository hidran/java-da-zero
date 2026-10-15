package com.shop.app;
import com.shop.catalog.Product;
public class Main {
    public static void main(String[] args) {
        Product coffee = new Product("Coffee", 250);
        Product tea = new Product("Tea", 180);
        System.out.println(coffee.name() + ": " + coffee.priceInCents());
        Order.Line[] lines = {new Order.Line(coffee, 2), new Order.Line(tea, 3)};
        Order order = new Order(lines);
        System.out.println("Total: " + order.totalInCents());
    }
}
