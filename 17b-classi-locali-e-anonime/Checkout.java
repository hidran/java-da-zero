public class Checkout {
    static void printPrices(int price) {
        class PercentOff implements PriceRule {
            private final int percent;
            PercentOff(int percent) {
                this.percent = percent;
            }
            @Override
            public int apply(int priceInCents) {
                return priceInCents - priceInCents * percent / 100;
            }
        }
        PriceRule tenOff = new PercentOff(10);
        PriceRule halfOff = new PercentOff(50);
        System.out.println("10% off: " + tenOff.apply(price));
        System.out.println("50% off: " + halfOff.apply(price));
        int coupon = price > 1500 ? 500 : 300;
        PriceRule welcome = new PriceRule() {
            @Override
            public int apply(int priceInCents) {
                return priceInCents - coupon;
            }
        };
        System.out.println("Welcome: " + welcome.apply(price));
    }
    public static void main(String[] args) {
        printPrices(2000);
        printPrices(1000);
    }
}
