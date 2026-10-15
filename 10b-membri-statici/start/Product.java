public class Product {
    private final String name;
    private final int priceInCents;
    public Product(String name, int priceInCents) {
        if (priceInCents < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.priceInCents = priceInCents;
    }
    public String getName() {
        return name;
    }
    public int getPriceInCents() {
        return priceInCents;
    }
}
