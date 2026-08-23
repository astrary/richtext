package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.phys.Vec2;

import javax.xml.stream.XMLStreamReader;
import java.util.Random;

public record Shaking(float amplitudeX, float amplitudeY) implements RichStyle {
    public static final String TAG = "shake";
    public static final Codec<Shaking> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("amplitudeX").forGetter(Shaking::amplitudeX),
            Codec.FLOAT.fieldOf("amplitudeY").forGetter(Shaking::amplitudeY)
        ).apply(instance, Shaking::new)
    );
    public static final MapCodec<Shaking> MAP_CODEC = CODEC.fieldOf("shake");
    private static final Random rng = new Random();

    public static Shaking fromReader(XMLStreamReader reader) {
        var rawAmp = reader.getAttributeValue(null, "amp");
        var rawAmpX = reader.getAttributeValue(null, "ampX");
        var rawAmpY = reader.getAttributeValue(null, "ampY");

        var ampX = ConversionUtil.toFloatOrDefault(rawAmpX);
        var ampY = ConversionUtil.toFloatOrDefault(rawAmpY);

        if (rawAmp != null && !rawAmp.isEmpty()) {
            var amp = ConversionUtil.toFloatOrDefault(rawAmp);

            ampX = amp;
            ampY = amp;
        }

        if (ampX <= 0.0) {
            ampX = Float.MIN_VALUE;
        }
        if (ampY <= 0.0) {
            ampY = Float.MIN_VALUE;
        }

        return new Shaking(ampX, ampY);
    }

    @Override
    public String type() {
        return TAG;
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        fx.offset = new Vec2(rng.nextFloat(-amplitudeX, amplitudeX), rng.nextFloat(-amplitudeY, amplitudeY));

        return fx;
    }
}
