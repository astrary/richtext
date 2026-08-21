package com.astrary.richtext.mixin;

import com.astrary.richtext.RichTextMod;
import com.astrary.richtext.text.RichTextContents;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.ComponentSerialization;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Arrays;

@Mixin(ComponentSerialization.class)
public class ComponentSerializationMixin {
    @ModifyVariable(method = "createCodec", at = @At("STORE"), ordinal = 0)
    private static ComponentContents.Type<?>[] injectCustomType(ComponentContents.Type<?>[] types) {
        var customTypes = Arrays.copyOf(types, types.length + 1);
        customTypes[types.length] = RichTextContents.TYPE;

        RichTextMod.LOGGER.debug("registered RichTextContents codec");

        return customTypes;
    }
}
