package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.TextColor;

import javax.xml.stream.XMLStreamReader;
import java.awt.Color;

public record Rainbow(float frequency, float speed) implements RichStyle {
    public static final String TAG = "rainbow";

    public static final Codec<Rainbow> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("freq").forGetter(Rainbow::frequency),
            Codec.FLOAT.fieldOf("speed").forGetter(Rainbow::speed)
        ).apply(instance, Rainbow::new)
    );
    public static final MapCodec<Rainbow> MAP_CODEC = CODEC.fieldOf("rainbow");

    @Override
    public String type() {
        return TAG;
    }

    public static Rainbow fromReader(XMLStreamReader reader) {
        var rawFrequency = reader.getAttributeValue(null, "freq");
        var rawSpeed = reader.getAttributeValue(null, "speed");

        var frequency = ConversionUtil.toFloatOrDefault(rawFrequency, 2.0f);
        var speed = ConversionUtil.toFloatOrDefault(rawSpeed);

        if (frequency < 0.0) {
            frequency = Float.MIN_VALUE;
        }
        if (speed < 0.0) {
            speed = Float.MIN_VALUE;
        }

        return new Rainbow(frequency, speed);
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        var i = (float) fx.getCharacterPosition();
        var hue = (float) ((getTime() + i * 0.1f * frequency) % 1.0f);
        var color = Color.HSBtoRGB(hue, 0.8f, 1.0f);

        fx.color = TextColor.fromRgb(color);

        return fx;
    }

    @Override
    public double getTime() {
        return RichStyle.super.getTime() * speed;
    }
}
