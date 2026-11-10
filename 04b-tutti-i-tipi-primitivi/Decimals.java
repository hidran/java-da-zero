public class Decimals {
    public static void main(String[] args) {
        float floatRevenue = 1234567.89f;
        double doubleRevenue = 1234567.89;
        System.out.println("float: " + floatRevenue);
        System.out.println("double: " + doubleRevenue);
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        double perDay = 500.0 / 0.0;
        double unknown = 0.0 / 0.0;
        System.out.println("per day: " + perDay + ", unknown: " + unknown);
        System.out.println("NaN equals itself: " + (unknown == unknown));
        System.out.println("isNaN: " + Double.isNaN(unknown));
    }
}
