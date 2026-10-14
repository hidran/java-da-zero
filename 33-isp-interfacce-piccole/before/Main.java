public class Main {
    public static void main(String[] args) {
        OrderOperations archive = new ArchiveOrders();
        new ReportJob(archive).run();
        archive.printReceipt(1);
        try {
            archive.cancel(1);
        } catch (UnsupportedOperationException e) {
            System.out.println("Not supported: " + e.getMessage());
        }
    }
}
