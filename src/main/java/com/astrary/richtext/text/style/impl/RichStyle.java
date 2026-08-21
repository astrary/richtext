package com.astrary.richtext.text.style.impl;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.CharFxInstance;
import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Style;

import javax.xml.stream.XMLStreamReader;

// TODO: outline
// TODO: wave
// TODO: gradient
// TODO: particles
// TODO: static rainbow, gradient rainbow
// TODO: gradient customization: wave width, direction, etc
// TODO: better shake: speed, lerp (vibration)
// TODO: breath

public sealed interface RichStyle permits Bold, Color, Italic, Obfuscated, RandomColor, Shaking, Strikethrough, Underlined {
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
                        default -> throw new UnsupportedOperationException("unimplemented type: " + type);
                    }
            );

    static boolean isValidTag(String tag) {
        return switch (tag) {
            case Italic.TAG, Bold.TAG, Color.TAG, Strikethrough.TAG, Underlined.TAG, Obfuscated.TAG, RandomColor.TAG, Shaking.TAG -> true;
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
}

