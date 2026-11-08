import java.math.BigDecimal;
import java.math.RoundingMode;
public record VatCalculator(BigDecimal rate, RoundingMode rounding) {
    public static final BigDecimal ITALIAN_STANDARD_RATE = new BigDecimal("0.22");
    public BigDecimal vatOn(BigDecimal net) {
        return net.multiply(rate).setScale(2, rounding);
    }
    public BigDecimal grossOf(BigDecimal net) {
        return net.add(vatOn(net));
    }
}
