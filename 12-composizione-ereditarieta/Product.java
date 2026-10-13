public class Product {
    private final String name;
    private final int priceInCents;
    public Product(String name, int priceInCents) {
        this.name = name;
        this.priceInCents = priceInCents;
    }
    public String getName() {
        return name;
    }
    public int getPriceInCents() {
        return priceInCents;
    }
    public String describe() {
        return name + ", " + priceInCents + " cents";
    }
}
