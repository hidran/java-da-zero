import java.util.EnumMap;
import java.util.Map;
public class OrderBoard {
    enum OrderStatus { NEW, PAID, SHIPPED }
    public static void main(String[] args) {
        Map<OrderStatus, Integer> counts = new EnumMap<>(OrderStatus.class);
        counts.put(OrderStatus.SHIPPED, 5);
        counts.put(OrderStatus.NEW, 2);
        counts.put(OrderStatus.PAID, 3);
        System.out.println("Counts: " + counts);
    }
}
