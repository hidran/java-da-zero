public interface Discount {
    int apply(int priceInCents);
    default int saving(int priceInCents) {
        return priceInCents - apply(priceInCents);
    }
    static int percentOf(int priceInCents, int percent) {
        return priceInCents * percent / 100;
    }
}
