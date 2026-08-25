package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ColorUtil;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.TextColor;

import javax.xml.stream.XMLStreamReader;

public record Gradient(TextColor from, TextColor to, float frequency, float speed) implements RichStyle {
    public static final String TAG = "gradient";

    public static final Codec<Gradient> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            TextColor.CODEC.fieldOf("from").forGetter(Gradient::from),
            TextColor.CODEC.fieldOf("to").forGetter(Gradient::from),
            Codec.FLOAT.fieldOf("freq").forGetter(Gradient::frequency),
            Codec.FLOAT.fieldOf("speed").forGetter(Gradient::speed)
        ).apply(instance, Gradient::new)
    );
    public static final MapCodec<Gradient> MAP_CODEC = CODEC.fieldOf("gradient");

    public static Gradient fromReader(XMLStreamReader reader) {
        var rawFromColor = reader.getAttributeValue(null, "from");
        var rawToColor = reader.getAttributeValue(null, "to");
        var rawFrequency = reader.getAttributeValue(null, "freq");
        var rawSpeed = reader.getAttributeValue(null, "speed");

        var fromColor = ConversionUtil.toTextColorOrDefault(rawFromColor, TextColor.fromRgb(0x444444));
        var toColor = ConversionUtil.toTextColorOrDefault(rawToColor);
        var frequency = ConversionUtil.toFloatOrDefault(rawFrequency, 0.3f);
        var speed = ConversionUtil.toFloatOrDefault(rawSpeed);

        if (frequency < 0.0) {
            frequency = Float.MIN_VALUE;
        }
        if (speed <= 0.0) {
            speed = Float.MIN_VALUE;
        }
        if (fromColor == toColor) {
            fromColor = TextColor.fromRgb(fromColor.getValue() - 1);
        }

        return new Gradient(fromColor, toColor, frequency, speed);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        var t = Math.sin(getTime() + fx.getCharacterPosition() * frequency) * 0.5 + 0.5;

        var color = ColorUtil.lerpColor(from.getValue(), to.getValue(), (float) t);
        fx.color = TextColor.fromRgb(color);

        return fx;
    }

    @Override
    public double getTime() {
        return RichStyle.super.getTime() * speed;
    }
}
