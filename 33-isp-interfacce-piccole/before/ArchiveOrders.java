public class ArchiveOrders implements OrderOperations {
    @Override
    public void create(String item) {
        throw new UnsupportedOperationException("create");
    }
    @Override
    public void cancel(int id) {
        throw new UnsupportedOperationException("cancel");
    }
    @Override
    public void refund(int id) {
        throw new UnsupportedOperationException("refund");
    }
    @Override
    public void printReceipt(int id) {
    }
    @Override
    public String exportCsv() {
        return "Book,Pen";
    }
}
