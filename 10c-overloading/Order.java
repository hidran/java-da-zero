public class Order {
    private int itemCount;
    private int totalInCents;
    public void add(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        itemCount += quantity;
        totalInCents += product.getPriceInCents() * quantity;
    }
    public void add(Product product) {
        add(product, 1);
    }
    public int getItemCount() {
        return itemCount;
    }
    public int getTotalInCents() {
        return totalInCents;
    }
}
