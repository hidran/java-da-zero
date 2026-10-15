public class Main {
    public static void main(String[] args) {
        Product book = new Product("Book", 1500);
        Product sticker = new Product("Sticker");
        System.out.println(sticker.getName() + ": " + sticker.getPriceInCents());
        Order order = new Order();
        order.add(book);
        order.add(sticker);
        order.add(new Product("Pen", 200), 3);
        System.out.println("Items: " + order.getItemCount());
        System.out.println("Total: " + order.getTotalInCents());
        System.out.println("Products created: " + Product.getCreatedCount());
    }
}
