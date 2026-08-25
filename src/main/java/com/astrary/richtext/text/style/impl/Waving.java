package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.phys.Vec2;

import javax.xml.stream.XMLStreamReader;

public record Waving(float frequency, float amplitude, float speed) implements RichStyle {
    public static final String TAG = "wave";

    public static final Codec<Waving> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("freq").forGetter(Waving::frequency),
            Codec.FLOAT.fieldOf("amp").forGetter(Waving::amplitude),
            Codec.FLOAT.fieldOf("speed").forGetter(Waving::speed)
        ).apply(instance, Waving::new)
    );
    public static final MapCodec<Waving> MAP_CODEC = CODEC.fieldOf("wave");

    public static Waving fromReader(XMLStreamReader reader) {
        var rawFrequency = reader.getAttributeValue(null, "freq");
        var rawAmplitude = reader.getAttributeValue(null, "amp");
        var rawSpeed = reader.getAttributeValue(null, "speed");

        var frequency = ConversionUtil.toFloatOrDefault(rawFrequency);
        var amplitude = ConversionUtil.toFloatOrDefault(rawAmplitude);
        var speed = ConversionUtil.toFloatOrDefault(rawSpeed);

        if (frequency < 0.0) {
            frequency = Float.MIN_VALUE;
        }
        if (amplitude < 0.0) {
            amplitude = Float.MIN_VALUE;
        }
        if (speed <= 0.0) {
            speed = Float.MIN_VALUE;
        }

        return new Waving(frequency, amplitude, speed);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        var i = (float) fx.getCharacterPosition();
        var loopLength = 5.0f;

        var waveLength = 1.0f;
        var offset = i * 0.1f * waveLength;
        var t = (getTime() + offset) % loopLength / loopLength;
        var angle = (float) t * Math.TAU;

        var x = (float) Math.cos(angle * 2.0f * frequency) * 2.0f * amplitude;
        var y = (float) Math.sin(angle * 2.0f * frequency) * 2.0f * amplitude;

        fx.offset = fx.offset.add(new Vec2(x, y));

        return fx;
    }

    @Override
    public double getTime() {
        return RichStyle.super.getTime() * speed;
    }
}
