public class Main {
    public static void main(String[] args) {
        var mug = new Product("Mug", 1200);
        var ebook = new DigitalProduct("Ebook", 1500, "shop.example/ebook");
        System.out.println(mug.describe());
        System.out.println(ebook.describe());
        System.out.println("Inherited name: " + ebook.getName());
        var order = new Order(new Customer("Anna"), ebook, 2);
        System.out.println(order.summary());
        System.out.println("Total: " + order.totalInCents() + " cents");
    }
}
