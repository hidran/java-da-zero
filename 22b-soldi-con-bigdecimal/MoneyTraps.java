import java.math.BigDecimal;
public class MoneyTraps {
    public static void main(String[] args) {
        BigDecimal two = new BigDecimal("2.0");
        BigDecimal twoWithCents = new BigDecimal("2.00");
        System.out.println("equals: " + two.equals(twoWithCents));
        System.out.println("compareTo: " + two.compareTo(twoWithCents));
        BigDecimal total = new BigDecimal("10.00");
        total.add(new BigDecimal("5.00"));
        System.out.println("Lost: " + total);
        total = total.add(new BigDecimal("5.00"));
        System.out.println("Kept: " + total);
    }
}
