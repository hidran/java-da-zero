import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
public class Main {
    public static void main(String[] args) {
        List<Product> products = List.of(
                new Product("Pen", "office", 150),
                new Product("Notebook", "office", 400),
                new Product("Stapler", "office", 650),
                new Product("Coffee", "food", 250),
                new Product("Tea", "food", 300),
                new Product("Book", "books", 1500),
                new Product("Comic", "books", 800));
        Map<String, Long> countByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, TreeMap::new,
                        Collectors.counting()));
        System.out.println("Count: " + countByCategory);
        Map<String, Integer> totalByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::category, TreeMap::new,
                        Collectors.summingInt(Product::priceInCents)));
        System.out.println("Total: " + totalByCategory);
        List<List<String>> pages = products.stream()
                .map(Product::name)
                .gather(Gatherers.windowFixed(2))
                .toList();
        System.out.println("Pages: " + pages);
    }
}
