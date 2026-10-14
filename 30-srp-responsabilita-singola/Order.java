import java.util.List;
public record Order(String customer, List<OrderLine> lines) {
}
record OrderLine(String product, int priceInCents, int quantity) {
}
