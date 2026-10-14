import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
public class VirtualThreads {
    static int chargeCoffee() throws InterruptedException {
        Thread.sleep(Duration.ofSeconds(1));
        return 250;
    }
    public static void main(String[] args) throws Exception {
        Thread first = Thread.ofVirtual().start(() -> System.out.println("Order received"));
        first.join();
        System.out.println("Virtual: " + first.isVirtual());
        List<Future<Integer>> payments = new ArrayList<>();
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < 10_000; i++) {
                payments.add(executor.submit(() -> chargeCoffee()));
            }
        }
        int total = 0;
        for (Future<Integer> payment : payments) {
            total += payment.get();
        }
        System.out.println("Payments: " + payments.size());
        System.out.println("Total cents: " + total);
    }
}
