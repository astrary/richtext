package com.astrary.richtext.mixin;

import com.astrary.richtext.RichTextMod;
import com.astrary.richtext.text.RichTextContents;
import com.astrary.richtext.text.RichTextProcessor;
import com.astrary.richtext.text.style.impl.RichStyle;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import javax.xml.stream.XMLStreamException;
import java.util.List;
import java.util.Set;

@Mixin(Component.class)
public interface ComponentMixin {
    /**
     * @author astrary, richtext
     * @reason rich style formatter
     */
    @Overwrite
    static MutableComponent literal(String text) {
        if ((text.contains("<") && !text.contains(">")) || (text.contains(">") && !text.contains("<")))
            return MutableComponent.create(PlainTextContents.create(text));
        if (text.contains("<>")) return MutableComponent.create(PlainTextContents.create(text));

        List<Pair<String, List<RichStyle>>> richText;
        try {
            richText = RichTextProcessor.processString(text);
        } catch (XMLStreamException e) {
            RichTextMod.LOGGER.warn("failed to process rich string: {}", e.toString());
            return MutableComponent.create(PlainTextContents.create(text));
        }

        return MutableComponent.create(RichTextContents.create(richText));
    }
}
