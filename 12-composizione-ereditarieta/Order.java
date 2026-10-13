public record Order(Customer customer, Product product, int quantity) {
    public int totalInCents() {
        return product.getPriceInCents() * quantity;
    }
    public String summary() {
        return customer.name() + ": " + quantity + " x " + product.getName();
    }
}
record Customer(String name) {
}
