public class UnsignedValues {
    public static void main(String[] args) {
        byte fromFile = (byte) 200;
        System.out.println("signed: " + fromFile);
        int unsigned = Byte.toUnsignedInt(fromFile);
        int masked = fromFile & 0xFF;
        System.out.println("unsigned: " + unsigned + ", masked: " + masked);
        int big = Integer.parseUnsignedInt("4000000000");
        System.out.println("as int: " + big);
        System.out.println("as unsigned: " + Integer.toUnsignedString(big));
        System.out.println("compare: " + Integer.compare(big, 1));
        System.out.println("compareUnsigned: " + Integer.compareUnsigned(big, 1));
        System.out.println("half: " + Integer.divideUnsigned(big, 2));
        System.out.println(">> 2: " + (-16 >> 2) + ", >>> 2: " + (-16 >>> 2));
    }
}
