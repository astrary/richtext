package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ColorUtil;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.TextColor;

import javax.xml.stream.XMLStreamReader;

public record Shine(float frequency, float threshold, TextColor flashColor, float speed) implements RichStyle {
    public static final String TAG = "shine";

    public static final Codec<Shine> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("frequency").forGetter(Shine::frequency),
            Codec.FLOAT.fieldOf("threshold").forGetter(Shine::threshold),
            TextColor.CODEC.fieldOf("color").forGetter(Shine::flashColor),
            Codec.FLOAT.fieldOf("speed").forGetter(Shine::speed)
        ).apply(instance, Shine::new)
    );
    public static final MapCodec<Shine> MAP_CODEC = CODEC.fieldOf("shine");

    public static Shine fromReader(XMLStreamReader reader) {
        var rawFrequency = reader.getAttributeValue(null, "freq");
        var rawThreshold = reader.getAttributeValue(null, "threshold");
        var rawFlashColor = reader.getAttributeValue(null, "color");
        var rawSpeed = reader.getAttributeValue(null, "speed");

        var frequency = ConversionUtil.toFloatOrDefault(rawFrequency);
        var threshold = ConversionUtil.toFloatOrDefault(rawThreshold, 0.9f);
        var flashColor = ConversionUtil.toTextColorOrDefault(rawFlashColor);
        var speed = ConversionUtil.toFloatOrDefault(rawSpeed);

        if (frequency <= 0.0) {
            frequency = Float.MIN_VALUE;
        }
        if (speed <= 0.0) {
            speed = Float.MIN_VALUE;
        }

        return new Shine(frequency, threshold, flashColor, speed);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        // arbitrary seed
        var phase = ((double) fx.getCharacterPosition() * 43758.5453) % 10.0;
        var shine = (float) Math.sin(getTime() * frequency + phase);

        if (shine > threshold) {
            var intensity = (shine - 0.9f) * 10.0f;
            var newColor = ColorUtil.lerpColor(fx.color.getValue(), flashColor.getValue(), intensity);

            fx.color = TextColor.fromRgb(newColor);

            var scale = 1.0f + (intensity * 0.2f);
            fx.scale *= scale;
        }

        return fx;
    }

    @Override
    public double getTime() {
        return RichStyle.super.getTime() * speed;
    }
}
