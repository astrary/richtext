package com.astrary.richtext.text;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.style.StyleInstancer;
import com.astrary.richtext.text.style.impl.RichStyle;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RichTextBuilder {
    private boolean bold = false;
    private boolean italic = false;
    private boolean obfuscated = false;
    private boolean strikethrough = false;
    private boolean underline = false;
    private TextColor color = null;
    private String insertion = null;
    private ClickEvent clickEvent = null;
    private HoverEvent hoverEvent = null;
    private ResourceLocation font = null;
    private List<RichStyle> richStyles = new ArrayList<>();

    public RichTextBuilder() {
    }

    public static RichTextBuilder create() {
        return new RichTextBuilder();
    }

    public static RichTextBuilder fromStyle(Style style) {
        return create()
                .bold(style.isBold())
                .italic(style.isItalic())
                .obfuscated(style.isObfuscated())
                .strikethrough(style.isStrikethrough())
                .underline(style.isUnderlined())
                .color(style.getColor())
                .insertion(style.getInsertion())
                .clickEvent(style.getClickEvent())
                .hoverEvent(style.getHoverEvent())
                .font(style.getFont())
                .richStyles(((IStyleRichExtension) style).richtext$getRichStyle());
    }

    public RichTextBuilder bold(@Nullable Boolean value) {
        this.bold = Boolean.TRUE.equals(value);

        return this;
    }

    public RichTextBuilder italic(@Nullable Boolean value) {
        this.italic = Boolean.TRUE.equals(value);

        return this;
    }

    public RichTextBuilder color(@Nullable TextColor value) {
        this.color = value;

        return this;
    }

    public RichTextBuilder obfuscated(@Nullable Boolean value) {
        this.obfuscated = Boolean.TRUE.equals(value);

        return this;
    }

    public RichTextBuilder underline(@Nullable Boolean value) {
        this.underline = Boolean.TRUE.equals(value);

        return this;
    }

    public RichTextBuilder strikethrough(@Nullable Boolean value) {
        this.strikethrough = Boolean.TRUE.equals(value);

        return this;
    }

    public RichTextBuilder clickEvent(@Nullable ClickEvent value) {
        this.clickEvent = value;

        return this;
    }

    public RichTextBuilder hoverEvent(@Nullable HoverEvent value) {
        this.hoverEvent = value;

        return this;
    }

    public RichTextBuilder insertion(@Nullable String value) {
        this.insertion = value;

        return this;
    }

    public RichTextBuilder font(@Nullable ResourceLocation value) {
        this.font = value;

        return this;
    }

    public RichTextBuilder richStyle(@Nullable RichStyle value) {
        if (value != null) {
            this.richStyles.add(value);
        }

        return this;
    }

    public RichTextBuilder richStyles(@Nullable List<RichStyle> value) {
        if (value != null) {
            this.richStyles.addAll(value);
        }

        return this;
    }

    public Style build() {
        return StyleInstancer.newRichInstance(color, bold, italic, underline, strikethrough, obfuscated, clickEvent, hoverEvent, insertion, font, richStyles);
    }
}
