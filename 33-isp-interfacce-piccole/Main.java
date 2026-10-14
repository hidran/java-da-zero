public class Main {
    public static void main(String[] args) {
        ShopOrders orders = new ShopOrders();
        Cashier cashier = new Cashier(orders);
        cashier.sell("Coffee");
        cashier.sell("Tea");
        new ReportJob(orders).run();
        OrderExporter archive = () -> "Book,Pen";
        new ReportJob(archive).run();
    }
}
