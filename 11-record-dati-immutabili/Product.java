public record Product(String name, int priceInCents) {
    public Product {
        if (priceInCents < 0) {
            throw new IllegalArgumentException("Negative price: " + priceInCents);
        }
    }
    public Product withDiscount(int percent) {
        int discount = priceInCents * percent / 100;
        return new Product(name, priceInCents - discount);
    }
}
