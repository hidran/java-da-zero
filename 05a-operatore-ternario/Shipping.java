public class Shipping {
    public static void main(String[] args) {
        int thresholdInCents = 5000;
        int totalInCents = 7200;
        int shippingInCents = totalInCents >= thresholdInCents ? 0 : 500;
        System.out.println("Shipping: " + shippingInCents + " cents");
    }
}
