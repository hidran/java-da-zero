public class BlackFridayRule implements DiscountRule {
    @Override
    public int discountInCents(CustomerType type, int totalInCents) {
        return totalInCents * 10 / 100;
    }
}
