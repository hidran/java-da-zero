import java.util.ArrayList;
import java.util.List;
public record Order(String customer, List<String> lines, String coupon,
        boolean gift) {
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String customer;
        private final List<String> lines = new ArrayList<>();
        private String coupon = "none";
        private boolean gift;
        public Builder customer(String customer) {
            this.customer = customer;
            return this;
        }
        public Builder line(String product) {
            lines.add(product);
            return this;
        }
        public Builder coupon(String coupon) {
            this.coupon = coupon;
            return this;
        }
        public Builder gift() {
            gift = true;
            return this;
        }
        public Order build() {
            if (customer == null || lines.isEmpty()) {
                throw new IllegalStateException("Incomplete order");
            }
            return new Order(customer, List.copyOf(lines), coupon, gift);
        }
    }
    public static void main(String[] args) {
        Order plain = new Order("Marco", List.of("Book"), "none", false);
        Order built = Order.builder()
                .customer("Anna")
                .line("Pen")
                .line("Coffee")
                .coupon("WELCOME10")
                .gift()
                .build();
        System.out.println(plain);
        System.out.println(built);
    }
}
