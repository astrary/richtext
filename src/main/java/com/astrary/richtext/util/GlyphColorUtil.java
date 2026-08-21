package com.astrary.richtext.util;

import net.minecraft.network.chat.TextColor;

public class GlyphColorUtil {
    public static TextColor fromGlyphColor(float red, float green, float blue, float dimFactor) {
        int r = Math.round(red / dimFactor * 255.0f);
        int g = Math.round(green / dimFactor * 255.0f);
        int b = Math.round(blue / dimFactor * 255.0f);
        int color = (r << 16) | (g << 8) | b;

        return TextColor.fromRgb(color);
    }
}
