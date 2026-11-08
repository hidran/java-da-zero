import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
class PriceCalculatorTest {
    @Test
    void multipliesPriceByQuantity() {
        assertThat(PriceCalculator.total(1200, 3)).isEqualTo(3600);
    }
    @Test
    void appliesPercentDiscount() {
        assertThat(PriceCalculator.applyDiscount(3600, 10)).isEqualTo(3240);
    }
    @Test
    void zeroDiscountKeepsTheAmount() {
        assertThat(PriceCalculator.applyDiscount(3600, 0)).isEqualTo(3600);
    }
}
