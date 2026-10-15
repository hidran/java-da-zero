import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
class PriceCalculatorTest {
    private final PriceCalculator calculator = new PriceCalculator();
    @Test
    void totalMultipliesPriceByQuantity() {
        int unitPriceInCents = 250;
        int quantity = 3;
        int total = calculator.totalInCents(unitPriceInCents, quantity);
        assertThat(total).isEqualTo(750);
    }
    @Test
    void totalRejectsZeroQuantity() {
        assertThatThrownBy(() -> calculator.totalInCents(250, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Quantity must be positive");
    }
    @ParameterizedTest
    @CsvSource({
            "1000, 0, 1000",
            "1000, 10, 900",
            "2000, 25, 1500",
            "800, 100, 0"
    })
    void discountLowersPrice(int priceInCents, int percent, int expected) {
        int result = calculator.applyDiscount(priceInCents, percent);
        assertThat(result).isEqualTo(expected);
    }
}
