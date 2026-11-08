import java.math.BigDecimal;
public class MoneyBasics {
    public static void main(String[] args) {
        double sum = 0.1 + 0.2;
        System.out.println("double: " + sum);
        System.out.println("Is it 0.3? " + (sum == 0.3));
        int priceInCents = 150_000_000;
        int quantity = 20;
        System.out.println("int: " + priceInCents * quantity);
        long exact = Math.multiplyExact((long) priceInCents, quantity);
        System.out.println("long: " + exact);
        System.out.println("From double: " + new BigDecimal(0.1));
        System.out.println("From String: " + new BigDecimal("0.1"));
        System.out.println("valueOf: " + BigDecimal.valueOf(0.1));
    }
}
