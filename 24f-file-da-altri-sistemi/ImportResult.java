import java.util.List;
public record ImportResult(List<Product> products,
        List<Rejected> rejected) {
    public record Rejected(int lineNumber, String line, String reason) {
    }
}
