import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
public class Catalog {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Pen", 150),
                new Product("Mug", 800),
                new Product("Book", 1200),
                new Product("Cap", 800)));
        Comparator<Product> byPrice =
                Comparator.comparingInt(Product::priceInCents);
        Comparator<Product> cheapFirst =
                byPrice.thenComparing(Product::name);
        products.sort(cheapFirst);
        System.out.println("Cheap first: " + products);
        products.sort(cheapFirst.reversed());
        System.out.println("Expensive first: " + products);
        Set<Product> onePerPrice = new TreeSet<>(byPrice);
        onePerPrice.addAll(products);
        System.out.println("One per price: " + onePerPrice);
        List<String> tags = Arrays.asList("sale", null, "new");
        tags.sort(Comparator.nullsLast(Comparator.naturalOrder()));
        System.out.println("Tags: " + tags);
    }
}
