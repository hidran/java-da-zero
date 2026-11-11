public class CartChecks {
    static void expect(int expected, int actual) {
        if (expected != actual) {
            String message = "expected " + expected + ", got " + actual;
            throw new IllegalStateException(message);
        }
    }
    @Check
    void threeCoffees() {
        expect(750, Prices.total(250, 3));
    }
    @Check
    void twentyPercentOff() {
        expect(800, Prices.discounted(1000, 20));
    }
    @Check(enabled = false)
    void freeShipping() {
        throw new UnsupportedOperationException("not ready");
    }
}
