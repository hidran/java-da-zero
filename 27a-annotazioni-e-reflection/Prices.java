public class Prices {
    static int total(int priceInCents, int quantity) {
        return priceInCents * quantity;
    }
    static int discounted(int priceInCents, int percent) {
        return priceInCents * percent / 100;
    }
}
