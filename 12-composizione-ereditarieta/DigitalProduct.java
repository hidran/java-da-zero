public class DigitalProduct extends Product {
    private final String downloadLink;
    public DigitalProduct(String name, int priceInCents, String link) {
        super(name, priceInCents);
        this.downloadLink = link;
    }
    @Override
    public String describe() {
        return super.describe() + ", link " + downloadLink;
    }
}
