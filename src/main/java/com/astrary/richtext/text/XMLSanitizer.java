package com.astrary.richtext.text;

import com.astrary.richtext.text.style.impl.RichStyle;

import java.util.Optional;
import java.util.regex.Pattern;

public class XMLSanitizer {
    private static final Pattern TAG_PATTERN = Pattern.compile("</?([a-zA-Z_][\\w\\-.]*)\\b[^>]*>");

    public static Optional<String> sanitize(String text) {
        if (!checkValid(text)) return Optional.empty();

        text = text.replace("&", "&amp;");

        var matcher = TAG_PATTERN.matcher(text);
        var out = new StringBuilder();
        var end = 0;

        while (matcher.find()) {
            out.append(text, end, matcher.start());
            var tag = matcher.group(1);

            var group = matcher.group();
            if (RichStyle.isValidTag(tag)) {
                out.append(group);
            } else {
                out.append(group.replace("<", "&lt;").replace(">", "&gt;"));
            }

            end = matcher.end();
        }

        out.append(text.substring(end));

        return Optional.of(out.toString());
    }

    public static boolean checkValid(String text) {
        if ((text.contains("<") && !text.contains(">")) || (text.contains(">") && !text.contains("<")))
            return false;
        return !text.contains("<>");
    }
}
