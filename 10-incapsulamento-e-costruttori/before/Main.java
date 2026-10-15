public class Main {
    public static void main(String[] args) {
        Product book = new Product();
        book.name = "Book";
        book.priceInCents = 1500;
        System.out.println(book.name + ": " + book.priceInCents);
        book.priceInCents = -500;
        System.out.println(book.name + ": " + book.priceInCents);
    }
}
