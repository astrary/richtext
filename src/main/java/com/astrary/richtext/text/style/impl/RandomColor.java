package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.TextColor;

import java.util.Random;

public record RandomColor() implements RichStyle {
    public static final String TAG = "rcolor";
    public static final MapCodec<RandomColor> CODEC = MapCodec.unit(RandomColor::new);
    private static final Random rng = new Random();

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        fx.color = TextColor.fromRgb(rng.nextInt(0, 0xFFFFFF));

        return fx;
    }
}
