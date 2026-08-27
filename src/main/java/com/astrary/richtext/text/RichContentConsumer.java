package com.astrary.richtext.text;

import com.astrary.richtext.RichTextMod;
import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.style.impl.RichStyle;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public record RichContentConsumer<T>(FormattedText.StyledContentConsumer<T> defaultConsumer) implements FormattedText.StyledContentConsumer<T> {
    @Override
    public @NotNull Optional<T> accept(@NotNull Style mcStyle, @NotNull String text) {
        List<Pair<String, List<RichStyle>>> richText = RichTextProcessor.processString(text).orElse(null);
        if (richText == null) {
            RichTextMod.LOGGER.trace("failed to process rich string: {}", text);

            return defaultConsumer.accept(mcStyle, text);
        }

        var oldStyle = mcStyle.applyTo(Style.EMPTY);

        for (var pair : richText) {
            mcStyle = oldStyle;

            var str = pair.getFirst();
            var styles = pair.getSecond();

            for (var style : styles) {
                mcStyle = style.applyStyle(mcStyle);
                mcStyle = ((IStyleRichExtension) mcStyle).richtext$withRichStyle(style);
            }

            var result = defaultConsumer.accept(mcStyle, str);
            if (result.isPresent()) return result;
        }

        return Optional.empty();
    }
}
