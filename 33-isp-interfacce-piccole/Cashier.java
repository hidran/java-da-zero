public class Cashier {
    private final OrderCreator orders;
    public Cashier(OrderCreator orders) {
        this.orders = orders;
    }
    public void sell(String item) {
        orders.create(item);
        System.out.println("Sold: " + item);
    }
}
