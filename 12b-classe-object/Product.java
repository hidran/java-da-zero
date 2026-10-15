import java.util.Objects;
public class Product {
    private final String name;
    private final int priceInCents;
    public Product(String name, int priceInCents) {
        this.name = name;
        this.priceInCents = priceInCents;
    }
    @Override
    public String toString() {
        return "Product[name=" + name + ", priceInCents=" + priceInCents + "]";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product other = (Product) o;
        return priceInCents == other.priceInCents && Objects.equals(name, other.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name, priceInCents);
    }
}
