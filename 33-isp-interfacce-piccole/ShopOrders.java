import java.util.ArrayList;
import java.util.List;
public class ShopOrders implements OrderCreator, OrderCanceller,
        ReceiptPrinter, OrderExporter {
    private final List<String> items = new ArrayList<>();
    @Override
    public void create(String item) {
        items.add(item);
    }
    @Override
    public void cancel(int id) {
        items.set(id - 1, "cancelled");
    }
    @Override
    public void refund(int id) {
        System.out.println("Refund for order " + id);
    }
    @Override
    public void printReceipt(int id) {
        System.out.println("Receipt: " + items.get(id - 1));
    }
    @Override
    public String exportCsv() {
        return String.join(",", items);
    }
}
