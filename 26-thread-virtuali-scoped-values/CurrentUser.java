public class CurrentUser {
    static final ScopedValue<String> USER = ScopedValue.newInstance();
    static String placeOrder(String product) {
        return USER.get() + " ordered " + product;
    }
    public static void main(String[] args) {
        ScopedValue.where(USER, "anna").run(() -> System.out.println(placeOrder("Coffee")));
        ScopedValue.where(USER, "marco").run(() -> System.out.println(placeOrder("Book")));
        System.out.println("Bound outside: " + USER.isBound());
    }
}
