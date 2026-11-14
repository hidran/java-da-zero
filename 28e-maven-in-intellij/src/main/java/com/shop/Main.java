package com.shop;
public class Main {
    public static void main(String[] args) {
        String[] products = {"Mug", "Tea", "Spoon"};
        int[] pricesInCents = {1200, 450, 300};
        int[] quantities = {2, 1, 4};
        int totalInCents = 0;
        for (int i = 0; i < products.length; i++) {
            int lineInCents = pricesInCents[i] * quantities[i];
            System.out.println(products[i] + ": " + lineInCents + " cents");
            totalInCents += lineInCents;
        }
        System.out.println("Order total: " + totalInCents + " cents");
    }
}
