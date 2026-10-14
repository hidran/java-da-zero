public class PhysicalProduct extends Product implements Shippable {
    public PhysicalProduct(String name, int priceInCents) {
        super(name, priceInCents);
    }
    @Override
    public int shippingCostInCents() {
        return 500;
    }
}
