public class DigitalProduct extends Product {
    public DigitalProduct(String name, int priceInCents) {
        super(name, priceInCents);
    }
    @Override
    public int shippingCostInCents() {
        throw new UnsupportedOperationException("Cannot be shipped");
    }
}
