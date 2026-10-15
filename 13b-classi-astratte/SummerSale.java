public class SummerSale implements Discount {
    @Override
    public int apply(int priceInCents) {
        return priceInCents - Discount.percentOf(priceInCents, 20);
    }
}
