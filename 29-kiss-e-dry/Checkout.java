public class Checkout {
    public static void main(String[] args) {
        int coffeeOnSale = discounted(1200, 10);
        System.out.println("Coffee beans: " + coffeeOnSale);
        int bookOnSale = discounted(3000, 20);
        System.out.println("Java book: " + bookOnSale);
        int cart = coffeeOnSale + bookOnSale;
        System.out.println("Cart: " + cart);
        System.out.println("Member pays: " + total(cart, true));
        System.out.println("Guest pays: " + total(cart, false));
    }
    static int discounted(int priceInCents, int percent) {
        return priceInCents - priceInCents * percent / 100;
    }
    static int total(int cartInCents, boolean member) {
        if (member) {
            return discounted(cartInCents, 5);
        }
        if (cartInCents >= 5000) {
            return cartInCents;
        }
        return cartInCents + 490;
    }
}
