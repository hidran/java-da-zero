public class Product {
    String name;
    int priceInCents;
    String describe() {
        return name + ": " + priceInCents + " cents";
    }
    int priceFor(int quantity) {
        return priceInCents * quantity;
    }
}
