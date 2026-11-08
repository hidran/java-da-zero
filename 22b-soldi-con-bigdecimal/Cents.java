import java.math.BigDecimal;
public final class Cents {
    private Cents() {
    }
    public static long lineTotal(long priceInCents, int quantity) {
        return Math.multiplyExact(priceInCents, quantity);
    }
    public static BigDecimal toEuro(long cents) {
        return BigDecimal.valueOf(cents, 2);
    }
}
