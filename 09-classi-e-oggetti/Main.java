public class Main {
    public static void main(String[] args) {
        Product coffee = new Product();
        coffee.name = "Coffee";
        coffee.priceInCents = 250;
        Product book = new Product();
        book.name = "Book";
        book.priceInCents = 1500;
        System.out.println(coffee.describe());
        System.out.println(book.describe());
        System.out.println("Three coffees: " + coffee.priceFor(3));
        Product sameCoffee = coffee;
        sameCoffee.priceInCents = 300;
        System.out.println(coffee.describe());
        Product tea = new Product();
        System.out.println(tea.describe());
    }
}
