import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
public class Main {
    public static void main(String[] args) {
        Supplier<Product> gift = () -> new Product("Sticker", 0);
        List<Product> products = new ArrayList<>(List.of(
                new Product("Pen", 150),
                new Product("Book", 1200),
                new Product("Coffee", 250),
                gift.get()));
        Predicate<Product> isFree = p -> p.priceInCents() == 0;
        products.removeIf(isFree);
        PriceRule discount = cents -> cents * 90 / 100;
        Function<Product, String> label = Product::name;
        Consumer<Product> print = p -> {
            int price = discount.apply(p.priceInCents());
            System.out.println(label.apply(p) + ": " + price);
        };
        products.forEach(print);
        System.out.println("Products left: " + products.size());
    }
}
