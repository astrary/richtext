package com.astrary.richtext.mixin;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.RichTextBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@SuppressWarnings("DataFlowIssue")
@Mixin(Style.class)
public class StyleMixin {
//    @Inject(method = "getColor", at = @At("RETURN"), cancellable = true)
//    private void getColor(CallbackInfoReturnable<TextColor> cir) {
//        if (((IStyleRichExtension) this).richtext$isRandomColor()) {
//            cir.setReturnValue(TextColor.fromRgb(new Random().nextInt(0, 0xFFFFFF)));
//        }
//    }

    @Inject(method = "withColor(Lnet/minecraft/network/chat/TextColor;)Lnet/minecraft/network/chat/Style;", at = @At("RETURN"), cancellable = true)
    public void withColor(TextColor color, CallbackInfoReturnable<Style> cir) {
        var richStyle = RichTextBuilder.fromStyle((Style) (Object) this)
                .color(color)
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
            var newStyle = style == Style.EMPTY ? ((Style) (Object) this) : RichTextBuilder.fromStyle(style).build();

            cir.setReturnValue(newStyle);
        }
    }
}
