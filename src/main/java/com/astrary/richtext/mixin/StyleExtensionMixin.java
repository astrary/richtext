package com.astrary.richtext.mixin;

import com.astrary.richtext.ext.IStyleRichExtension;
import com.astrary.richtext.text.RichTextBuilder;
import com.astrary.richtext.text.style.impl.RichStyle;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(Style.class)
public class StyleExtensionMixin implements IStyleRichExtension {
    @Unique
    private final List<RichStyle> richtext$richStyles = new ArrayList<>();

    @Override
    public List<RichStyle> richtext$getRichStyle() {
        return new ArrayList<>(richtext$richStyles);
    }

    @Override
    public boolean richtext$addRichStyle(RichStyle value) {
        return this.richtext$richStyles.add(value);
    }

    @Override
    public Style richtext$withRichStyle(RichStyle value) {
        var builder = RichTextBuilder.fromStyle((Style) (Object) this);

        if (!this.richtext$getRichStyle().contains(value))
            builder = builder.richStyle(value);

        return builder.build();
    }
}
