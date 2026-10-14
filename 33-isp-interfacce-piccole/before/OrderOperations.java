public interface OrderOperations {
    void create(String item);
    void cancel(int id);
    void refund(int id);
    void printReceipt(int id);
    String exportCsv();
}
