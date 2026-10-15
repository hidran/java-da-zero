public class Order extends Product {
    private final String customerName;
    public Order(String customerName, String productName, int priceInCents) {
        super(productName, priceInCents);
        this.customerName = customerName;
    }
}
