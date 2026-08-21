package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

public record Underlined() implements RichStyle {
    public static final String TAG = "u";
    public static final MapCodec<Underlined> CODEC = MapCodec.unit(Underlined::new);

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withUnderlined(true);
    }
}
