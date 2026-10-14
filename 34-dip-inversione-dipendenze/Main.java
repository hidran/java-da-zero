public class Main {
    public static void main(String[] args) {
        OrderService shop = new OrderService(new StripePayment());
        shop.placeOrder("A-1", 2500);
        FakePayment fake = new FakePayment();
        OrderService tested = new OrderService(fake);
        tested.placeOrder("T-1", 1200);
        tested.placeOrder("T-2", 3000);
        System.out.println("fake charges: " + fake.charges());
    }
}
