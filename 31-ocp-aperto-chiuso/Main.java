import java.util.List;
public class Main {
    public static void main(String[] args) {
        var calculator = new DiscountCalculator(List.of(
                new VipRule(),
                new StudentRule()));
        for (CustomerType type : CustomerType.values()) {
            int discount = calculator.discountInCents(type, 10000);
            System.out.println(type + " discount: " + discount);
        }
    }
}
