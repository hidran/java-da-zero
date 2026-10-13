@FunctionalInterface
public interface PriceRule {
    int apply(int priceInCents);
}
