package org.swc;

public class BitMask {
    public static void main(String args[]) throws Exception {

        int a = 3;
        int b = 4;
        int c = a & b;
        a = a & (1 << 2);
        System.out.println(c);

    }
}
