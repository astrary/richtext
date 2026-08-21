package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

import javax.xml.stream.XMLStreamReader;

public record Color(int value) implements RichStyle {
    public static final String TAG = "color";
    public static final MapCodec<Color> CODEC = Codec.INT.fieldOf("value").xmap(Color::new, Color::value);

    public Color() {
        this(0xFFFFFF);
    }

    public static Color fromHexString(String color) {
        if (color.startsWith("#")) {
            color = color.substring(1);
        }

        if (color.length() != 6) {
            return new Color();
        }

        try {
            return new Color(Integer.parseInt(color, 16));
        } catch (NumberFormatException ex) {
            return new Color();
        }
    }

    public static Color fromReader(XMLStreamReader reader) {
        var rawValue = reader.getAttributeValue(null, "value");

        if (rawValue == null || rawValue.isEmpty()) {
            return new Color();
        }

        return Color.fromHexString(rawValue);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withColor(value);
    }
}
