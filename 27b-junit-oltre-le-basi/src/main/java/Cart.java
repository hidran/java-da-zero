import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Clock;
import java.time.LocalDate;
public class Cart {
    private final Clock clock;
    private int itemCount;
    private int totalInCents;
    public Cart(Clock clock) {
        this.clock = clock;
    }
    public void add(int priceInCents, int quantity) {
        itemCount += quantity;
        totalInCents += priceInCents * quantity;
    }
    public int itemCount() {
        return itemCount;
    }
    public int totalInCents() {
        return totalInCents;
    }
    public Path saveReceipt(Path folder) throws IOException {
        Path receipt = folder.resolve("receipt.txt");
        String text = LocalDate.now(clock) + " total " + totalInCents;
        return Files.writeString(receipt, text);
    }
}
