import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
@DisplayName("Cart")
class CartTest {
    private final Clock clock =
            Clock.fixed(Instant.parse("2026-11-07T10:15:00Z"), ZoneId.of("UTC"));
    private Cart cart;
    @BeforeEach
    void createCart() {
        cart = new Cart(clock);
    }
    @Nested
    @DisplayName("when empty")
    class WhenEmpty {
        @Test
        @DisplayName("has a zero total")
        void hasZeroTotal() {
            assertThat(cart.totalInCents()).isZero();
        }
    }
    @Nested
    @DisplayName("with two products")
    class WithTwoProducts {
        @BeforeEach
        void addProducts() {
            cart.add(150, 2);
            cart.add(400, 1);
        }
        @Test
        @DisplayName("counts items and sums the total")
        void countsItemsAndSumsTotal() {
            assertSoftly(softly -> {
                softly.assertThat(cart.itemCount()).isEqualTo(3);
                softly.assertThat(cart.totalInCents()).isEqualTo(700);
            });
        }
        @Test
        @DisplayName("saves a dated receipt")
        void savesDatedReceipt(@TempDir Path folder) throws IOException {
            Path receipt = cart.saveReceipt(folder);
            assertThat(Files.readString(receipt)).isEqualTo("2026-11-07 total 700");
        }
    }
}
