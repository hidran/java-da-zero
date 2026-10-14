import java.util.List;
public class DiscountCalculator {
    private final List<DiscountRule> rules;
    public DiscountCalculator(List<DiscountRule> rules) {
        this.rules = rules;
    }
    public int discountInCents(CustomerType type, int totalInCents) {
        return rules.stream()
                .mapToInt(rule -> rule.discountInCents(type, totalInCents))
                .sum();
    }
}
