package com.astrary.richtext.mixin;

import com.astrary.richtext.Config;
import com.astrary.richtext.RichTextMod;
import com.astrary.richtext.text.RichContentConsumer;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Mixin(TranslatableContents.class)
public class TranslatableContentsMixin {
    @Redirect(method = "visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/FormattedText;visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;"))
    private <T> Optional<T> richVisit(FormattedText instance, FormattedText.StyledContentConsumer<T> consumer, Style style) {
        if (!Config.ENABLED.get())
            return instance.visit(consumer, style);

        return instance.visit(new RichContentConsumer<>(consumer), style);
    }

    @Redirect(method = "decompose", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList$Builder;build()Lcom/google/common/collect/ImmutableList;"))
    private ImmutableList<FormattedText> decomposeFix(ImmutableList.Builder<FormattedText> instance, @Local String translatedKey) {
        if (!Config.ENABLED.get())
            return instance.build();

        ImmutableList.Builder<FormattedText> newResult = ImmutableList.builder();

        var splitTranslation = List.of(translatedKey.split("%s"));
        var args = new ArrayList<>(
            instance.build().stream()
            .filter(x -> !splitTranslation.contains(x.getString()))
            .toList()
        );

        new RichContentConsumer<Unit>((style, text) -> {
            var lastIndex = 0;
            var currentIndex = 0;
            var argIndex = 0;

            while ((currentIndex = text.indexOf("%s", lastIndex)) != -1) {
                if (currentIndex > lastIndex) {
                    var subText = text.substring(lastIndex, currentIndex).replace("%%", "%");

                    newResult.add(FormattedText.of(subText, style));
                }

                if (argIndex < args.size()) {
                    var arg = args.get(argIndex);

                    newResult.add(new FormattedText() {
                        @Override
                        public <T> @NotNull Optional<T> visit(@NotNull ContentConsumer<T> consumer) {
                            return arg.visit(consumer);
                        }

                        @Override
                        public <T> @NotNull Optional<T> visit(@NotNull StyledContentConsumer<T> consumer, @NotNull Style localStyle) {
                            return arg.visit(consumer, style.applyTo(localStyle));
                        }
                    });

                    argIndex += 1;
                } else {
                    newResult.add(FormattedText.of("%s", style));
                }

                lastIndex = currentIndex + 2;
            }

            if (lastIndex < text.length()) {
                var subText = text.substring(lastIndex).replace("%%", "%");

                newResult.add(FormattedText.of(subText, style));
            }

            return Optional.empty();
        }).accept(Style.EMPTY, translatedKey);

        return newResult.build();
    }
}
