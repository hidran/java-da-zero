public class Shipping {
    @FunctionalInterface
    interface ShippingStrategy {
        int cost(int totalInCents);
    }
    static int checkout(int totalInCents, ShippingStrategy strategy) {
        return totalInCents + strategy.cost(totalInCents);
    }
    public static void main(String[] args) {
        ShippingStrategy standard = total -> 500;
        ShippingStrategy express = total -> 1200;
        ShippingStrategy promo = total -> total >= 5000 ? 0 : 500;
        System.out.println("Standard: " + checkout(4000, standard));
        System.out.println("Express: " + checkout(4000, express));
        System.out.println("Promo: " + checkout(6000, promo));
    }
}
