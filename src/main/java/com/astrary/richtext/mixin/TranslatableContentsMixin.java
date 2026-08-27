package com.astrary.richtext.mixin;

import com.astrary.richtext.Config;
import com.astrary.richtext.text.RichContentConsumer;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.chat.contents.TranslatableFormatException;
import net.minecraft.util.Unit;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.regex.Pattern;

@Mixin(TranslatableContents.class)
public abstract class TranslatableContentsMixin {
    @Shadow
    @Final
    private static Pattern FORMAT_PATTERN;

    @Shadow
    protected abstract FormattedText getArgument(int index);

    @Redirect(method = "visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/chat/FormattedText;visit(Lnet/minecraft/network/chat/FormattedText$StyledContentConsumer;Lnet/minecraft/network/chat/Style;)Ljava/util/Optional;"))
    private <T> Optional<T> richVisit(FormattedText instance, FormattedText.StyledContentConsumer<T> consumer, Style style) {
        return instance.visit(new RichContentConsumer<>(consumer), style);
    }

    @Redirect(method = "decompose", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList$Builder;build()Lcom/google/common/collect/ImmutableList;"))
    private ImmutableList<FormattedText> decomposeFix(ImmutableList.Builder<FormattedText> instance, @Local String translatedKey) {
        ImmutableList.Builder<FormattedText> newResult = ImmutableList.builder();

        final int[] argIndex = {0};
        new RichContentConsumer<Unit>((style, text) -> {
            var matcher = FORMAT_PATTERN.matcher(text);
            var lastIndex = 0;
            int currentEnd;

            for (lastIndex = 0; matcher.find(lastIndex); lastIndex = currentEnd) {
                int currentStart = matcher.start();
                currentEnd = matcher.end();

                if (currentStart > lastIndex) {
                    var subText = text.substring(lastIndex, currentStart);
                    if (subText.indexOf(37) != -1) {
                        throw new IllegalArgumentException();
                    }

                    newResult.add(FormattedText.of(subText, style));
                }

                var fmtType = matcher.group(2);
                var s1 = text.substring(currentStart, currentEnd);
                if ("%".equals(fmtType) && "%%".equals(s1)) {
                    newResult.add(FormattedText.of("%", style));
                } else {
                    if (!"s".equals(fmtType)) {
                        throw new TranslatableFormatException(((TranslatableContents) (Object) this), "Unsupported format: '" + s1 + "'");
                    }

                    var rawArgIndex = matcher.group(1);
                    var argumentIndex = rawArgIndex != null ? Integer.parseInt(rawArgIndex) - 1 : argIndex[0]++;
                    var arg = this.getArgument(argumentIndex);

                    newResult.add(new FormattedText() {
                        @Override
                        public <T> @NotNull Optional<T> visit(@NotNull ContentConsumer<T> consumer) {
                            return arg.visit(consumer);
                        }

                        @Override
                        public <T> @NotNull Optional<T> visit(@NotNull StyledContentConsumer<T> consumer, @NotNull Style localStyle) {
                            var newStyle = Config.VANILLA_ARGUMENT_FORMATTING_ENABLED.getAsBoolean() ? localStyle : style.applyTo(localStyle);

                            return arg.visit(consumer, newStyle);
                        }
                    });
                }
            }

            if (lastIndex < text.length()) {
                var subText = text.substring(lastIndex);
                if (subText.indexOf(37) != -1) {
                    throw new IllegalArgumentException();
                }

                newResult.add(FormattedText.of(subText, style));
            }

            return Optional.empty();
        }).accept(Style.EMPTY, translatedKey);

        return newResult.build();
    }
}
