public class FindSlot {
    public static void main(String[] args) {
        int[][] stock = {{4, 0, 2, 6}, {1, 3, 0, 0}, {5, 2, 9, 1}};
        int emptyShelf = -1;
        int emptySlot = -1;
        search: for (int shelf = 0; shelf < stock.length; shelf++) {
            for (int slot = 0; slot < stock[shelf].length; slot++) {
                if (stock[shelf][slot] == 0) {
                    emptyShelf = shelf;
                    emptySlot = slot;
                    break search;
                }
            }
        }
        System.out.printf("Break: shelf %d, slot %d%n", emptyShelf, emptySlot);
        System.out.println("Return: " + findEmpty(stock));
    }
    static String findEmpty(int[][] stock) {
        for (int shelf = 0; shelf < stock.length; shelf++) {
            for (int slot = 0; slot < stock[shelf].length; slot++) {
                if (stock[shelf][slot] == 0) {
                    return "shelf %d, slot %d".formatted(shelf, slot);
                }
            }
        }
        return "none";
    }
}
