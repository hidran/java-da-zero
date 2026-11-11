import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class SalesTally {
    public static void main(String[] args) throws InterruptedException {
        Map<String, Integer> sold = new ConcurrentHashMap<>();
        Runnable till = () -> {
            for (int i = 0; i < 1_000; i++) {
                sold.merge("Coffee", 1, Integer::sum);
                sold.merge("Tea", 2, Integer::sum);
            }
        };
        Thread one = Thread.ofPlatform().start(till);
        Thread two = Thread.ofPlatform().start(till);
        one.join();
        two.join();
        System.out.println("Coffee sold: " + sold.get("Coffee"));
        System.out.println("Tea sold: " + sold.get("Tea"));
    }
}
