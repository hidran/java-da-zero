public class ReportJob {
    private final OrderOperations orders;
    public ReportJob(OrderOperations orders) {
        this.orders = orders;
    }
    public void run() {
        System.out.println("Report: " + orders.exportCsv());
    }
}
