package com.astrary.richtext.text;

import com.astrary.richtext.text.style.impl.RichStyle;

import java.util.regex.Pattern;

public class XMLSanitizer {
    private static final Pattern TAG_PATTERN = Pattern.compile("</?([a-zA-Z_][\\w\\-.]*)\\b[^>]*>");

    public static String sanitize(String str) {
        str = str.replace("&", "&amp;");

        var matcher = TAG_PATTERN.matcher(str);
        var out = new StringBuilder();
        var end = 0;

        while (matcher.find()) {
            out.append(str, end, matcher.start());
            var tag = matcher.group(1);

            var group = matcher.group();
            if (RichStyle.isValidTag(tag)) {
                out.append(group);
            } else {
                out.append(group.replace("<", "&lt;").replace(">", "&gt;"));
            }

            end = matcher.end();
        }

        out.append(str.substring(end));

        return out.toString();
    }
}
