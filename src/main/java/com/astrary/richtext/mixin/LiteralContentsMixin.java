package com.astrary.richtext.mixin;

import com.astrary.richtext.Config;
import com.astrary.richtext.text.RichContentConsumer;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.PlainTextContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

@Mixin(PlainTextContents.LiteralContents.class)
public class LiteralContentsMixin {
    @Redirect(method = "visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;accept(Lnet/minecraft/network/chat/Style;Ljava/lang/String;)Ljava/util/Optional;"))
    private <T> Optional<T> richVisit(FormattedText.StyledContentConsumer<T> instance, Style style, String text) {
        if (!Config.ENABLED.get())
            return instance.accept(style, text);

        return new RichContentConsumer<>(instance).accept(style, text);
    }
}
