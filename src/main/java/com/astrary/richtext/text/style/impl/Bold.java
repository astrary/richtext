package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

public record Bold() implements RichStyle {
    public static final String TAG = "b";
    public static final MapCodec<Bold> CODEC = MapCodec.unit(Bold::new);

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withBold(true);
    }
}
