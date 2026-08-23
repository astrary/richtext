package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import javax.xml.stream.XMLStreamReader;

public record Pulse(float speed, float scale) implements RichStyle {
    public static final String TAG = "pulse";

    public static final Codec<Pulse> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("speed").forGetter(Pulse::speed),
            Codec.FLOAT.fieldOf("scale").forGetter(Pulse::scale)
        ).apply(instance, Pulse::new)
    );
    public static final MapCodec<Pulse> MAP_CODEC = CODEC.fieldOf("pulse");

    public static Pulse fromReader(XMLStreamReader reader) {
        var rawSpeed = reader.getAttributeValue(null, "speed");
        var rawScale = reader.getAttributeValue(null, "scale");

        var speed = ConversionUtil.toFloatOrDefault(rawSpeed);
        var scale = ConversionUtil.toFloatOrDefault(rawScale);

        if (speed <= 0.0) {
            speed = Float.MIN_VALUE;
        }
        if (scale <= 0.0) {
            scale = Float.MIN_VALUE;
        }

        return new Pulse(speed, scale);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        var scale = (float) Math.sin(getTime() * this.speed * 5.0f);
        fx.scale = 1.0f + (scale * 0.15f * this.scale);

        return fx;
    }
}
