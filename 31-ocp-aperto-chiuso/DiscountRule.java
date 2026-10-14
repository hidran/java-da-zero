public interface DiscountRule {
    int discountInCents(CustomerType type, int totalInCents);
}
