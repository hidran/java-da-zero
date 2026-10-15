public class Main {
    public static void main(String[] args) {
        System.out.println("Products: " + Product.getCreatedCount());
        Product book = new Product("Book", 1500);
        Product mug = new Product("Mug", 850);
        System.out.println(book.getName() + " and " + mug.getName());
        System.out.println("Products: " + Product.getCreatedCount());
        System.out.println("Book: " + PriceFormatter.format(book.getPriceInCents()));
        System.out.println("Mug: " + PriceFormatter.format(mug.getPriceInCents()));
        System.out.println("Max: " + PriceFormatter.format(Product.MAX_PRICE_IN_CENTS));
    }
}
