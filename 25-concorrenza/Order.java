import java.util.List;
public record Order(String id, List<Integer> pricesInCents) {
    public int total() {
        return pricesInCents.stream().mapToInt(Integer::intValue).sum();
    }
}
