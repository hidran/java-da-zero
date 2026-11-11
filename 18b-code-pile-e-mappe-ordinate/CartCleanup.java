import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
public class CartCleanup {
    public static void main(String[] args) {
        List<String> cart = new ArrayList<>(List.of("Tea", "Mug", "Pen", "Cup"));
        Set<String> soldOut = Set.of("Mug", "Cup");
        Iterator<String> it = cart.iterator();
        while (it.hasNext()) {
            if (soldOut.contains(it.next())) {
                it.remove();
            }
        }
        System.out.println("Cart: " + cart);
    }
}
