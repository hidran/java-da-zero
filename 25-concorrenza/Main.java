import java.util.List;
import java.util.concurrent.CompletableFuture;
public class Main {
    public static void main(String[] args) {
        Order first = new Order("A1", List.of(1200, 800, 450));
        Order second = new Order("B2", List.of(2500, 300));
        int shippingInCents = 500;
        var firstTotal = CompletableFuture.supplyAsync(first::total)
                .thenApply(total -> total + shippingInCents);
        var secondTotal = CompletableFuture.supplyAsync(second::total)
                .thenApply(total -> total + shippingInCents);
        int grandTotal = firstTotal.join() + secondTotal.join();
        System.out.println(first.id() + " with shipping: " + firstTotal.join());
        System.out.println(second.id() + " with shipping: " + secondTotal.join());
        System.out.println("Grand total: " + grandTotal);
    }
}
