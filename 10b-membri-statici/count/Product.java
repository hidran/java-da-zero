public class Product {
    private static int createdCount = 0;
    private final String name;
    private final int priceInCents;
    public Product(String name, int priceInCents) {
        if (priceInCents < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.name = name;
        this.priceInCents = priceInCents;
        createdCount++;
    }
    public String getName() {
        return name;
    }
    public int getPriceInCents() {
        return priceInCents;
    }
    public static int getCreatedCount() {
        return createdCount;
    }
}
