package com.astrary.richtext.ext;

import com.astrary.richtext.text.style.impl.RichStyle;
import net.minecraft.network.chat.Style;

import java.util.List;

public interface IStyleRichExtension {
    List<RichStyle> richtext$getRichStyle();
    boolean richtext$addRichStyle(RichStyle value);
    Style richtext$withRichStyle(RichStyle value);
}
