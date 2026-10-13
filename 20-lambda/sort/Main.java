import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>(List.of(
                new Product("Pen", 150),
                new Product("Book", 1200),
                new Product("Coffee", 250)));
        Comparator<Product> byPrice =
                (a, b) -> Integer.compare(a.priceInCents(), b.priceInCents());
        products.sort(byPrice);
        products.forEach(p -> System.out.println(p));
        products.sort(Comparator.comparing(Product::name));
        products.forEach(p -> System.out.println(p.name()));
    }
}
