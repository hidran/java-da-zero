import java.util.List;
public class Main {
    public static void main(String[] args) {
        Order order = new Order("Anna", List.of(
                new OrderLine("Book", 1500, 1),
                new OrderLine("Coffee", 250, 2)));
        new OrderService().checkout(order);
    }
}
