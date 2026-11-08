import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
public class Cart {
    public static void main(String[] args) {
        BigDecimal subtotal = Cents.toEuro(Cents.lineTotal(625, 3));
        BigDecimal rate = VatCalculator.ITALIAN_STANDARD_RATE;
        var halfUp = new VatCalculator(rate, RoundingMode.HALF_UP);
        var halfEven = new VatCalculator(rate, RoundingMode.HALF_EVEN);
        System.out.println("Subtotal: " + subtotal);
        System.out.println("VAT half up: " + halfUp.vatOn(subtotal));
        System.out.println("VAT half even: " + halfEven.vatOn(subtotal));
        var euro = NumberFormat.getCurrencyInstance(Locale.ITALY);
        System.out.println("To pay: " + euro.format(halfUp.grossOf(subtotal)));
        System.out.println("Big: " + euro.format(new BigDecimal("1234.50")));
    }
}
