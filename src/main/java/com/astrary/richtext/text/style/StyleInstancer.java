package com.astrary.richtext.text.style;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.mixin.StyleMixinAccessor;
import com.astrary.richtext.text.style.impl.RichStyle;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public final class StyleInstancer {
    public static Style newRichInstance(@Nullable TextColor color, @Nullable Boolean bold, @Nullable Boolean italic, @Nullable Boolean underlined, @Nullable Boolean strikethrough, @Nullable Boolean obfuscated, @Nullable ClickEvent clickEvent, @Nullable HoverEvent hoverEvent, @Nullable String insertion, @Nullable ResourceLocation font, List<RichStyle> richStyles) {
        var newStyle = StyleMixinAccessor.richtext$newInstance(color, bold, italic, underlined, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font);

        for (var richStyle : richStyles) {
            ((IStyleRichExtension) newStyle).richtext$addRichStyle(richStyle);
        }

        return newStyle;
    }
}
