public class Scores {
    public static void main(String[] args) {
        int[] scores = {7, 9, 6};
        scores[2] = 8;
        System.out.println("First: " + scores[0]);
        System.out.println("Length: " + scores.length);
        int total = 0;
        for (int i = 0; i < scores.length; i++) {
            total += scores[i];
        }
        System.out.println("Total: " + total);
        for (int score : scores) {
            System.out.println("Score: " + score);
        }
    }
}
