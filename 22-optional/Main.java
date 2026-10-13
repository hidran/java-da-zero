import java.util.List;
import java.util.Map;
import java.util.Optional;
public class Main {
    public static void main(String[] args) {
        Catalog catalog = new Catalog(List.of(
                new Product("Coffee", 250),
                new Product("Book", 1500)));
        Optional<Product> coffee = catalog.findByName("Coffee");
        Optional<Product> pizza = catalog.findByName("Pizza");
        coffee.ifPresent(p -> System.out.println("Found " + p.name()));
        System.out.println("Pizza missing: " + pizza.isEmpty());
        int price = pizza.map(Product::priceInCents).orElse(0);
        System.out.println("Pizza price: " + price);
        Product drink = pizza.orElseGet(() -> new Product("Water", 100));
        System.out.println("Suggested: " + drink.name());
        Product book = catalog.findByName("Book").orElseThrow();
        System.out.println("Book costs " + book.priceInCents());
        Map<String, Integer> stock = Map.of("Coffee", 12);
        int pizzaStock = Optional.ofNullable(stock.get("Pizza")).orElse(0);
        System.out.println("Pizza stock: " + pizzaStock);
    }
}
