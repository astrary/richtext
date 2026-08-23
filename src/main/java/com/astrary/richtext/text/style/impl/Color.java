package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

import javax.xml.stream.XMLStreamReader;

public record Color(TextColor value) implements RichStyle {
    public static final String TAG = "color";
    public static final MapCodec<Color> CODEC = TextColor.CODEC.fieldOf("value").xmap(Color::new, Color::value);

    public Color() {
        this(TextColor.fromRgb(0xFFFFFF));
    }

    public static Color fromReader(XMLStreamReader reader) {
        var rawValue = reader.getAttributeValue(null, "value");

        return new Color(ConversionUtil.toTextColorOrDefault(rawValue));
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
