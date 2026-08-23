package com.astrary.richtext.util;

import com.astrary.richtext.text.style.impl.Color;
import net.minecraft.network.chat.TextColor;

public class ConversionUtil {
    public static float toFloatOrDefault(String strValue, float defaultValue) {
        if (strValue == null || strValue.isEmpty()) {
            strValue = String.valueOf(defaultValue);
        }

        float value;
        try {
            value = Float.parseFloat(strValue);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }

        return value;
    }

    public static float toFloatOrDefault(String strValue) {
        return toFloatOrDefault(strValue, 1.0f);
    }

    public static TextColor toTextColorOrDefault(String strValue, TextColor defaultValue) {
        if (strValue == null || strValue.isEmpty()) {
            return defaultValue;
        }

        if (strValue.startsWith("#")) {
            strValue = strValue.substring(1);
        }

        if (strValue.length() != 6) {
            return defaultValue;
        }

        try {
            return TextColor.fromRgb(Integer.parseInt(strValue, 16));
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    public static TextColor toTextColorOrDefault(String strValue) {
        return toTextColorOrDefault(strValue, TextColor.fromRgb(0xFFFFFF));
    }
}
