package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.text.CharFxInstance;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Style;

import javax.xml.stream.XMLStreamReader;

// TODO: outline
// TODO: particles
// TODO: better shake: speed, lerp (vibration)

public sealed interface RichStyle permits Bold, Color, Gradient, Italic, Obfuscated, Pulse, Rainbow, RandomColor, Shaking, Strikethrough, Underlined, Waving {
    Codec<RichStyle> CODEC =
        Codec.STRING.dispatch(
            "type",
            RichStyle::type,
            type -> switch (type) {
                case Bold.TAG -> Bold.CODEC;
                case Italic.TAG -> Italic.CODEC;
                case Color.TAG -> Color.CODEC;
                case Strikethrough.TAG -> Strikethrough.CODEC;
                case Underlined.TAG -> Underlined.CODEC;
                case Obfuscated.TAG -> Obfuscated.CODEC;
                case RandomColor.TAG -> RandomColor.CODEC;
                case Shaking.TAG -> Shaking.MAP_CODEC;
                case Pulse.TAG -> Pulse.MAP_CODEC;
                case Rainbow.TAG -> Rainbow.MAP_CODEC;
                case Waving.TAG -> Waving.MAP_CODEC;
                case Gradient.TAG -> Gradient.MAP_CODEC;
                default -> throw new UnsupportedOperationException("unimplemented type: " + type);
            }
        );

    static boolean isValidTag(String tag) {
        return switch (tag) {
            case Italic.TAG, Bold.TAG, Color.TAG, Strikethrough.TAG, Underlined.TAG, Obfuscated.TAG, RandomColor.TAG,
                 Shaking.TAG, Pulse.TAG, Rainbow.TAG, Waving.TAG, Gradient.TAG -> true;
            default -> false;
        };
    }

    static RichStyle fromReader(XMLStreamReader reader) {
        var tag = reader.getLocalName();

        return switch (tag) {
            case Italic.TAG -> new Italic();
            case Bold.TAG -> new Bold();
            case Color.TAG -> Color.fromReader(reader);
            case Strikethrough.TAG -> new Strikethrough();
            case Obfuscated.TAG -> new Obfuscated();
            case Underlined.TAG -> new Underlined();
            case RandomColor.TAG -> new RandomColor();
            case Shaking.TAG -> Shaking.fromReader(reader);
            case Pulse.TAG -> Pulse.fromReader(reader);
            case Rainbow.TAG -> Rainbow.fromReader(reader);
            case Waving.TAG -> Waving.fromReader(reader);
            case Gradient.TAG -> Gradient.fromReader(reader);
            default -> throw new UnsupportedOperationException("unimplemented tag: " + tag);
        };
    }

    String type();

    default Style applyStyle(Style style) {
        return style;
    }

    default CharFxInstance process(CharFxInstance fx) {
        return fx;
    }

    default double getTime() {
        return System.currentTimeMillis() / 1000.0;
    }
}

