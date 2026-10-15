public class Product {
    public static final int MAX_PRICE_IN_CENTS = 100000;
    private static int createdCount = 0;
    private final String name;
    private final int priceInCents;
    public Product(String name, int priceInCents) {
        if (priceInCents < 0 || priceInCents > MAX_PRICE_IN_CENTS) {
            throw new IllegalArgumentException("Price out of range");
        }
        this.name = name;
        this.priceInCents = priceInCents;
        createdCount++;
    }
    public Product(String name) {
        this(name, 0);
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
