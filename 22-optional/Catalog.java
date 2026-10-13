import java.util.List;
import java.util.Optional;
public class Catalog {
    private final List<Product> products;
    public Catalog(List<Product> products) {
        this.products = List.copyOf(products);
    }
    public Optional<Product> findByName(String name) {
        for (Product product : products) {
            if (product.name().equals(name)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }
}
