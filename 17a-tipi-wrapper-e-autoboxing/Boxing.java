public class Boxing {
    public static void main(String[] args) {
        int quantity = 3;
        Integer boxed = quantity;
        int total = boxed + 2;
        System.out.println("Boxed: " + boxed + ", total: " + total);
        Object[] cart = {"Coffee", quantity};
        System.out.println("In the cart: " + cart[1].getClass());
        int parsed = Integer.parseInt("42");
        Integer valued = Integer.valueOf("42");
        System.out.println("Parsed: " + parsed + ", valued: " + valued);
        System.out.println("Max: " + Integer.MAX_VALUE);
        System.out.println("Max + 1: " + (Integer.MAX_VALUE + 1));
    }
}
