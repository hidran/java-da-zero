public class NewArrays {
    public static void main(String[] args) {
        int[] scores = new int[3];
        double[] prices = new double[3];
        boolean[] flags = new boolean[3];
        String[] names = new String[3];
        System.out.println("int: " + scores[0] + ", double: " + prices[0]);
        System.out.println("boolean: " + flags[0] + ", String: " + names[0]);
        for (int i = 0; i < scores.length; i++) {
            scores[i] = (i + 1) * 10;
        }
        for (int score : scores) {
            System.out.println("Score: " + score);
        }
    }
}
