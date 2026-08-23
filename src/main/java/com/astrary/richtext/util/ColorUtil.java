package com.astrary.richtext.util;

public class ColorUtil {
    public static int lerpColor(int a, int b, float t) {
        int aA = (a >>> 24) & 0xFF;
        int aR = (a >>> 16) & 0xFF;
        int aG = (a >>> 8) & 0xFF;
        int aB = a & 0xFF;

        int bA = (b >>> 24) & 0xFF;
        int bR = (b >>> 16) & 0xFF;
        int bG = (b >>> 8) & 0xFF;
        int bB = b & 0xFF;

        return ((int) (aA + (bA - aA) * t) << 24) |
            ((int) (aR + (bR - aR) * t) << 16) |
            ((int) (aG + (bG - aG) * t) << 8) |
            (int) (aB + (bB - aB) * t);
    }
}
