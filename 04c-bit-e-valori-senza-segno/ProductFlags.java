public class ProductFlags {
    public static void main(String[] args) {
        int newFlag = 0b001;
        int saleFlag = 0b010;
        int fragileFlag = 0b100;
        int flags = newFlag | fragileFlag;
        System.out.println("flags: " + Integer.toBinaryString(flags));
        System.out.println("on sale: " + ((flags & saleFlag) != 0));
        System.out.println("fragile: " + ((flags & fragileFlag) != 0));
        flags = flags | saleFlag;
        flags = flags & ~newFlag;
        System.out.println("sale, not new: " + Integer.toBinaryString(flags));
        flags = flags ^ fragileFlag;
        System.out.println("fragile toggled: " + Integer.toBinaryString(flags));
    }
}
