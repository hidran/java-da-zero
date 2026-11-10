public class CharAndByte {
    public static void main(String[] args) {
        char letter = 'A';
        int code = letter + 1;
        char next = (char) (letter + 1);
        System.out.println("code: " + code + ", next: " + next);
        char category = 'C';
        int position = category - 'A' + 1;
        System.out.println("position of C: " + position);
        byte level = 10;
        level += 1;
        level++;
        System.out.println("level: " + level);
    }
}
