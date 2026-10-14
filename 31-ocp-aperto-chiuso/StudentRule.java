public class StudentRule implements DiscountRule {
    @Override
    public int discountInCents(CustomerType type, int totalInCents) {
        if (type != CustomerType.STUDENT) {
            return 0;
        }
        return totalInCents * 10 / 100;
    }
}
