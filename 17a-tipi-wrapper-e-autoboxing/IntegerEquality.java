public class IntegerEquality {
    public static void main(String[] args) {
        Integer small = 127;
        Integer sameSmall = 127;
        System.out.println("127 == 127: " + (small == sameSmall));
        Integer big = 128;
        Integer sameBig = 128;
        System.out.println("128 == 128: " + (big == sameBig));
        System.out.println("128 equals: " + big.equals(sameBig));
        int plain = 128;
        System.out.println("int == Integer: " + (plain == big));
    }
}
