public class StockCheck {
    static Integer findStock(String name) {
        if (name.equals("Coffee")) {
            return 12;
        }
        return null;
    }
    public static void main(String[] args) {
        Integer coffee = findStock("Coffee");
        Integer tea = findStock("Tea");
        System.out.println("Coffee: " + coffee + ", tea: " + tea);
        if (tea != null) {
            System.out.println("Tea in stock: " + tea);
        } else {
            System.out.println("Tea: not tracked");
        }
        int teaCount = tea;
        System.out.println("Tea count: " + teaCount);
    }
}
