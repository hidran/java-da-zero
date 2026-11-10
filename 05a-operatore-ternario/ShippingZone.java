public class ShippingZone {
    public static void main(String[] args) {
        int zone = 3;
        int costInCents = switch (zone) {
            case 1 -> 490;
            case 2 -> 790;
            default -> 1290;
        };
        System.out.println("Zone " + zone + " costs " + costInCents + " cents");
    }
}
