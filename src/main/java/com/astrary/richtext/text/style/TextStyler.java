package com.astrary.richtext.text.style;

import com.astrary.richtext.text.style.impl.RichStyle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TextStyler {
    private final List<RichStyle> style = new ArrayList<>();

    public TextStyler() {
    }

    public void pushStyle(RichStyle richStyle) {
        style.add(richStyle);
    }

    public void popStyle(String tag) {
        style.removeIf(x -> x.type().equals(tag));
    }

    public List<RichStyle> getStyle() {
        return new ArrayList<>(this.style);
    }

    public String toString() {
        return style.stream().map(RichStyle::toString).collect(Collectors.joining(" & "));
    }
}
