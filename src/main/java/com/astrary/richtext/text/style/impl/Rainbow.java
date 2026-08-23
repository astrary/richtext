package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.ConversionUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.TextColor;

import javax.xml.stream.XMLStreamReader;
import java.awt.Color;

public record Rainbow(float frequency, float waveLength) implements RichStyle {
    public static final String TAG = "rainbow";

    public static final Codec<Rainbow> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.FLOAT.fieldOf("freq").forGetter(Rainbow::frequency),
            Codec.FLOAT.fieldOf("waveLength").forGetter(Rainbow::waveLength)
        ).apply(instance, Rainbow::new)
    );
    public static final MapCodec<Rainbow> MAP_CODEC = CODEC.fieldOf("rainbow");

    @Override
    public String type() {
        return TAG;
    }

    public static Rainbow fromReader(XMLStreamReader reader) {
        var rawFrequency = reader.getAttributeValue(null, "freq");
        var rawWaveLength = reader.getAttributeValue(null, "waveLength");

        var frequency = ConversionUtil.toFloatOrDefault(rawFrequency, 2.0f);
        var waveLength = ConversionUtil.toFloatOrDefault(rawWaveLength);

        if (frequency < 0.0) {
            frequency = Float.MIN_VALUE;
        }
        if (waveLength < 0.0) {
            waveLength = Float.MIN_VALUE;
        }

        return new Rainbow(frequency, waveLength);
    }

    @Override
    public CharFxInstance process(CharFxInstance fx) {
        var i = (float) fx.getCharacterPosition();
        var hue = (float) ((getTime() * frequency + i * 0.1f * waveLength) % 1.0f);
        var color = Color.HSBtoRGB(hue, 0.8f, 1.0f);

        fx.color = TextColor.fromRgb(color);

        return fx;
    }
}
