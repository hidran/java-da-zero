public class VipRule implements DiscountRule {
    @Override
    public int discountInCents(CustomerType type, int totalInCents) {
        if (type != CustomerType.VIP) {
            return 0;
        }
        return totalInCents * 20 / 100;
    }
}
