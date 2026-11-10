import java.math.BigInteger;
public class BigNumbers {
    public static void main(String[] args) {
        long ways20 = 2_432_902_008_176_640_000L;
        System.out.println("silent: " + ways20 * 21);
        BigInteger big = BigInteger.valueOf(ways20);
        BigInteger factor = BigInteger.valueOf(21 * 22 * 23 * 24 * 25);
        BigInteger ways25 = big.multiply(factor);
        System.out.println("25 products: " + ways25);
        System.out.println("exact: " + Math.multiplyExact(ways20, 21));
        System.out.println("the end");
    }
}
