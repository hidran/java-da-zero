public class Main {
    public static void main(String[] args) {
        Product book = new Product("Book", 1500);
        Product onSale = new Product("Book", book.getPriceInCents() - 300);
        System.out.println(book.getName() + ": " + book.getPriceInCents());
        System.out.println("On sale: " + onSale.getPriceInCents());
        Product broken = new Product("Broken", -500);
        System.out.println("Never printed: " + broken.getName());
    }
}
