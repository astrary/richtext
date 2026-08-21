package com.astrary.richtext.mixin;

import com.astrary.richtext.RichTextMod;
import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.GlyphColorUtil;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class StringRenderOutputMixin {
    @Shadow
    @Final
    private float dimFactor;

//    @Inject(method = "accept", at = @At("HEAD"))
//    private void setupRich(int positionInCurrentSequence, Style style, int codePoint, CallbackInfoReturnable<Boolean> cir) {
//
//    }

    @Redirect(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;renderChar(Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;ZZFFFLorg/joml/Matrix4f;Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFI)V"))
    private void renderChar(
        Font instance,
        BakedGlyph glyph,
        boolean bold,
        boolean italic,
        float boldOffset,
        float x,
        float y,
        Matrix4f matrix,
        VertexConsumer buffer,
        float red,
        float green,
        float blue,
        float alpha,
        int packedLight,
        @Local(argsOnly = true, ordinal = 0) int positionInCurrentSequence,
        @Local(argsOnly = true, ordinal = 0) Style style,
        @Local(argsOnly = true, ordinal = 1) int codePoint,

        @Local(ordinal = 1) LocalFloatRef localR,
        @Local(ordinal = 2) LocalFloatRef localG,
        @Local(ordinal = 3) LocalFloatRef localB
    ) {
        var charFx = new CharFxInstance(
            GlyphColorUtil.fromGlyphColor(red, green, blue, this.dimFactor),
            (char) codePoint,
            positionInCurrentSequence
        );
        var richStyles = ((IStyleRichExtension) style).richtext$getRichStyle();

        for (var richStyle : richStyles) {
            charFx = richStyle.process(charFx);
        }

        var textColor = charFx.color.getValue();
        red = (float) (textColor >> 16 & 255) / 255.0F * this.dimFactor;
        green = (float) (textColor >> 8 & 255) / 255.0F * this.dimFactor;
        blue = (float) (textColor & 255) / 255.0F * this.dimFactor;
        localR.set(red);
        localG.set(green);
        localB.set(blue);

        ((FontMixinAccessor) instance).richstyle$renderChar(
            glyph,
            bold,
            italic,
            boldOffset,
            x + charFx.offset.x,
            y + charFx.offset.y,
            matrix,
            buffer,
            red,
            green,
            blue,
            alpha,
            packedLight
        );
    }
}
