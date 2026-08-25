package com.astrary.richtext.mixin;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.RichTextBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@SuppressWarnings("DataFlowIssue")
@Mixin(Style.class)
public class StyleMixin {
    @Shadow
    @Final
    @Nullable
    private TextColor color;

    @Shadow
    @Final
    @Nullable
    private Boolean bold;

    @Shadow
    @Final
    @Nullable
    private Boolean italic;

    @Shadow
    @Final
    @Nullable
    private Boolean underlined;

    @Shadow
    @Final
    @Nullable
    private Boolean strikethrough;

    @Shadow
    @Final
    @Nullable
    private Boolean obfuscated;

    @Shadow
    @Final
    @Nullable
    private ClickEvent clickEvent;

    @Shadow
    @Final
    @Nullable
    private HoverEvent hoverEvent;

    @Shadow
    @Final
    @Nullable
    private String insertion;

    @Shadow
    @Final
    @Nullable
    private ResourceLocation font;

    @Inject(method = "withColor(Lnet/minecraft/network/chat/TextColor;)Lnet/minecraft/network/chat/Style;", at = @At("RETURN"), cancellable = true)
    public void withColor(TextColor color, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(color)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withColor(Lnet/minecraft/ChatFormatting;)Lnet/minecraft/network/chat/Style;", at = @At("RETURN"), cancellable = true)
    public void withColor(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(TextColor.fromLegacyFormat(formatting))
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withColor(I)Lnet/minecraft/network/chat/Style;", at = @At("RETURN"), cancellable = true)
    public void withColor(int rgb, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(TextColor.fromRgb(rgb))
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withBold", at = @At("RETURN"), cancellable = true)
    public void withBold(Boolean bold, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .bold(bold)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withItalic", at = @At("RETURN"), cancellable = true)
    public void withItalic(Boolean italic, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .italic(italic)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withUnderlined", at = @At("RETURN"), cancellable = true)
    public void withUnderlined(Boolean underlined, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .underline(underlined)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withStrikethrough", at = @At("RETURN"), cancellable = true)
    public void withStrikethrough(Boolean strikethrough, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .strikethrough(strikethrough)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withObfuscated", at = @At("RETURN"), cancellable = true)
    public void withObfuscated(Boolean obfuscated, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .obfuscated(obfuscated)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withClickEvent", at = @At("RETURN"), cancellable = true)
    public void withClickEvent(ClickEvent clickEvent, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .clickEvent(clickEvent)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withHoverEvent", at = @At("RETURN"), cancellable = true)
    public void withHoverEvent(HoverEvent hoverEvent, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .hoverEvent(hoverEvent)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withInsertion", at = @At("RETURN"), cancellable = true)
    public void withInsertion(String insertion, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .insertion(insertion)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "withFont", at = @At("RETURN"), cancellable = true)
    public void withFont(ResourceLocation fontId, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .font(fontId)
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "applyFormat", at = @At("RETURN"), cancellable = true)
    public void applyFormat(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        var style = cir.getReturnValue();

        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(style.getColor())
            .bold(style.isBold())
            .italic(style.isItalic())
            .strikethrough(style.isStrikethrough())
            .underline(style.isUnderlined())
            .obfuscated(style.isObfuscated())
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "applyLegacyFormat", at = @At("RETURN"), cancellable = true)
    public void applyLegacyFormat(ChatFormatting formatting, CallbackInfoReturnable<Style> cir) {
        var style = cir.getReturnValue();

        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(style.getColor())
            .bold(style.isBold())
            .italic(style.isItalic())
            .strikethrough(style.isStrikethrough())
            .underline(style.isUnderlined())
            .obfuscated(style.isObfuscated())
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "applyFormats", at = @At("RETURN"), cancellable = true)
    public void applyFormats(ChatFormatting[] formats, CallbackInfoReturnable<Style> cir) {
        var style = cir.getReturnValue();

        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
            .color(style.getColor())
            .bold(style.isBold())
            .italic(style.isItalic())
            .strikethrough(style.isStrikethrough())
            .underline(style.isUnderlined())
            .obfuscated(style.isObfuscated())
            .build();

        cir.setReturnValue(richStyle);
    }

    @Inject(method = "applyTo", at = @At("RETURN"), cancellable = true)
    public void applyTo(Style style, CallbackInfoReturnable<Style> cir) {
        if ((Style) (Object) this == Style.EMPTY) {
            cir.setReturnValue(style);
        } else {
            var text = RichTextBuilder
                .fromStyle(style)
                .color(this.color != null ? this.color : style.getColor())
                .bold(this.bold != null ? this.bold : style.isBold())
                .italic(this.italic != null ? this.italic : style.isItalic())
                .underline(this.underlined != null ? this.underlined : style.isUnderlined())
                .strikethrough(this.strikethrough != null ? this.strikethrough : style.isStrikethrough())
                .obfuscated(this.obfuscated != null ? this.obfuscated : style.isObfuscated())
                .clickEvent(this.clickEvent != null ? this.clickEvent : style.getClickEvent())
                .hoverEvent(this.hoverEvent != null ? this.hoverEvent : style.getHoverEvent())
                .insertion(this.insertion != null ? this.insertion : style.getInsertion())
                .font(this.font != null ? this.font : style.getFont())
                .richStyles(((IStyleRichExtension) this).richtext$getRichStyle())
                .build();
            var newStyle = style == Style.EMPTY ? ((Style) (Object) this) : text;

            cir.setReturnValue(newStyle);
        }
    }

    @Inject(method = "equals", at = @At("RETURN"), cancellable = true)
    public void equals(Object other, CallbackInfoReturnable<Boolean> cir) {
        var result = cir.getReturnValueZ();

        if (other instanceof IStyleRichExtension richStyle) {
            cir.setReturnValue(result && ((IStyleRichExtension) this).richtext$getRichStyle().equals(richStyle.richtext$getRichStyle()));
        }
    }

    /**
     * @author astrary
     * @reason richtext rich style extension
     */
    @Overwrite
    public int hashCode() {
        return Objects.hash(this.color, this.bold, this.italic, this.underlined, this.strikethrough, this.obfuscated, this.clickEvent, this.hoverEvent, this.insertion, ((IStyleRichExtension) this).richtext$getRichStyle());
    }
}
