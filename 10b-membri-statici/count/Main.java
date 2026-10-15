public class Main {
    public static void main(String[] args) {
        System.out.println("Products: " + Product.getCreatedCount());
        Product book = new Product("Book", 1500);
        Product mug = new Product("Mug", 850);
        System.out.println(book.getName() + " and " + mug.getName());
        System.out.println("Products: " + Product.getCreatedCount());
    }
}
