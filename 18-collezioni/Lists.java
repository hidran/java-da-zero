import java.util.ArrayList;
import java.util.List;
public class Lists {
    public static void main(String[] args) {
        List<String> cart = new ArrayList<>();
        cart.add("Coffee");
        cart.add("Book");
        cart.add("Coffee");
        cart.add("Pen");
        System.out.println(cart.size() + " items: " + cart);
        System.out.println("Second: " + cart.get(1));
        System.out.println("First: " + cart.getFirst());
        System.out.println("Last: " + cart.getLast());
        System.out.println("Reversed: " + cart.reversed());
        List<String> gifts = List.of("Tea", "Mug");
        try {
            gifts.add("Pen");
        } catch (UnsupportedOperationException e) {
            System.out.println("Read-only: " + gifts);
        }
    }
}
