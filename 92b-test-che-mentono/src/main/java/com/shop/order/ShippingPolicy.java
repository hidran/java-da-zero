package com.shop.order;

public class ShippingPolicy {
    private static final int FREE_FROM = 5000;
    private static final int REDUCED_FROM = 3000;
    private static final int REDUCED_FEE = 200;
    private static final int STANDARD_FEE = 500;

    public int feeInCents(int totalInCents) {
        if (totalInCents < 0) {
            throw new IllegalArgumentException("Total must not be negative");
        }
        if (totalInCents >= FREE_FROM) {
            return 0;
        }
        if (totalInCents >= REDUCED_FROM) {
            return REDUCED_FEE;
        }
        return STANDARD_FEE;
    }
}
