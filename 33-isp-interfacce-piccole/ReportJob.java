public class ReportJob {
    private final OrderExporter orders;
    public ReportJob(OrderExporter orders) {
        this.orders = orders;
    }
    public void run() {
        System.out.println("Report: " + orders.exportCsv());
    }
}
