public class Order {
    record Line(String product, int quantity) {
    }
    private final String customer;
    private final Line[] lines;
    Order(String customer, Line[] lines) {
        this.customer = customer;
        this.lines = lines;
    }
    class Cursor {
        private int next;
        boolean hasNext() {
            return next < lines.length;
        }
        String nextLabel() {
            Line line = lines[next];
            next++;
            return customer + ": " + line.quantity() + " " + line.product();
        }
    }
    Cursor cursor() {
        return new Cursor();
    }
    public static void main(String[] args) {
        Line[] items = {new Line("Coffee", 2), new Line("Tea", 3)};
        Order order = new Order("Ada", items);
        Cursor cursor = order.cursor();
        while (cursor.hasNext()) {
            System.out.println(cursor.nextLabel());
        }
    }
}
