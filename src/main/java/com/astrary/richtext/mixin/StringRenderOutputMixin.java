package com.astrary.richtext.mixin;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.CharFxInstance;
import com.astrary.richtext.util.GlyphColorUtil;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.font.SheetGlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.network.chat.Style;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(targets = "net.minecraft.client.gui.Font$StringRenderOutput")
public class StringRenderOutputMixin {
    @Shadow
    @Final
    private float dimFactor;

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
        @Local(ordinal = 3) LocalFloatRef localB,
        @Local(ordinal = 0) LocalRef<GlyphInfo> glyphInfoLocal
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

        var matrixCloned = new Matrix4f(matrix);

        if (charFx.isScaled()) {
            var oldGlyphInfoLocal = glyphInfoLocal.get();
            var finalScale = charFx.scale;

            glyphInfoLocal.set(new GlyphInfo() {
                @Override
                public float getAdvance() {
                    return oldGlyphInfoLocal.getAdvance() * finalScale;
                }

                @Override
                public float getAdvance(boolean bold) {
                    return oldGlyphInfoLocal.getAdvance(bold) * finalScale;
                }

                @Override
                public @NotNull BakedGlyph bake(@NotNull Function<SheetGlyphInfo, BakedGlyph> function) {
                    return oldGlyphInfoLocal.bake(function);
                }
            });

            matrixCloned.translate(x, y, 0.0f);
            matrixCloned.scale(finalScale, finalScale, 1.0f);
            matrixCloned.translate(-x, -y, 0.0f);
        }

        ((FontMixinAccessor) instance).richstyle$renderChar(
            glyph,
            bold,
            italic,
            boldOffset,
            x + charFx.offset.x,
            y + charFx.offset.y,
            matrixCloned,
            buffer,
            red,
            green,
            blue,
            alpha,
            packedLight
        );
    }
}
