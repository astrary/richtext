package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

public record Strikethrough() implements RichStyle {
    public static final String TAG = "s";
    public static final MapCodec<Strikethrough> CODEC = MapCodec.unit(Strikethrough::new);

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withStrikethrough(true);
    }
}
