package com.astrary.richtext.text.style.impl;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Style;

public record Obfuscated() implements RichStyle {
    public static final String TAG = "o";
    public static final MapCodec<Obfuscated> CODEC = MapCodec.unit(Obfuscated::new);

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public Style applyStyle(Style style) {
        return style.withObfuscated(true);
    }
}
