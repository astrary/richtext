package com.astrary.richtext.text;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.style.impl.RichStyle;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public record RichTextContents(List<Pair<String, List<RichStyle>>> richText) implements ComponentContents {
    public static final Codec<List<Pair<String, List<RichStyle>>>> MAP_CODEC =
            Codec.list(
                    Codec.pair(
                            Codec.STRING.fieldOf("text").codec(),
                            Codec.list(RichStyle.CODEC)
                                    .fieldOf("style").codec()
                    )
            );
    public static final MapCodec<RichTextContents> CODEC = RecordCodecBuilder.mapCodec((content) -> content.group(
            MAP_CODEC.fieldOf("richText")
                    .forGetter(RichTextContents::richText)
    ).apply(content, RichTextContents::create));

    public static final Type<RichTextContents> TYPE = new Type<>(CODEC, "richText");

    public static RichTextContents create(List<Pair<String, List<RichStyle>>> richText) {
        return new RichTextContents(richText);
    }

    @Override
    public <T> @NotNull Optional<T> visit(FormattedText.@NotNull StyledContentConsumer<T> consumer, @NotNull Style mcStyle) {
        var oldStyle = mcStyle.applyTo(Style.EMPTY);

        for (var pair : richText) {
            mcStyle = oldStyle;

            var str = pair.getFirst();
            var styles = pair.getSecond();

            for (var style : styles) {
                mcStyle = style.applyStyle(mcStyle);
                mcStyle = ((IStyleRichExtension) mcStyle).richtext$withRichStyle(style);
            }

            var result = consumer.accept(mcStyle, str);
            if (result.isPresent()) return result;
        }

        return Optional.empty();
    }

    @Override
    public <T> @NotNull Optional<T> visit(FormattedText.@NotNull ContentConsumer<T> consumer) {
        for (var pair : richText) {
            var str = pair.getFirst();

            var result = consumer.accept(str);
            if (result.isPresent()) return result;
        }

        return Optional.empty();
    }

    @Override
    public @NotNull Type<?> type() {
        return TYPE;
    }
}
