package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

public record Italic() implements RichStyle {
    public static final String TAG = "i";
    public static final MapCodec<Italic> CODEC = MapCodec.unit(Italic::new);

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withItalic(true);
    }
}
