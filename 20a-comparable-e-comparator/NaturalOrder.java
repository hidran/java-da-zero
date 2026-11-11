import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
public class NaturalOrder {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Pen", 150),
                new Product("Mug", 800),
                new Product("Book", 1200),
                new Product("Pen", 120)));
        Collections.sort(products);
        System.out.println("Sorted: " + products);
        Set<Product> shelf = new TreeSet<>(products);
        boolean added = shelf.add(new Product("Mug", 800));
        System.out.println("Added: " + added + ", size: " + shelf.size());
    }
}
