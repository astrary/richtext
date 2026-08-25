package com.astrary.richtext.text;

import com.astrary.richtext.text.style.TextStyler;
import com.astrary.richtext.text.style.impl.RichStyle;
import com.ctc.wstx.stax.WstxInputFactory;
import com.mojang.datafixers.util.Pair;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RichTextProcessor {
    public static Optional<List<Pair<String, List<RichStyle>>>> processString(String text) {
        var xml = XMLSanitizer.sanitize(text);
        if (xml.isEmpty()) return Optional.empty();

        try {
            return processStringInternal(xml.get());
        } catch (XMLStreamException e) {
            return Optional.empty();
        }
    }

    private static Optional<List<Pair<String, List<RichStyle>>>> processStringInternal(String text) throws XMLStreamException {
        var root = String.format("<root>%s</root>", text);

        var factory = new WstxInputFactory();
        var reader = factory.createXMLStreamReader(new StringReader(root));

        var styler = new TextStyler();
        var collection = new ArrayList<Pair<String, List<RichStyle>>>();

        while (reader.hasNext()) {
            int event = reader.next();

            if ((event == XMLStreamConstants.START_ELEMENT || event == XMLStreamConstants.END_ELEMENT) && !RichStyle.isValidTag(reader.getLocalName()))
                continue;

            switch (event) {
                case XMLStreamConstants.START_ELEMENT: {
                    var style = RichStyle.fromReader(reader);

                    styler.pushStyle(style);

                    break;
                }
                case XMLStreamConstants.END_ELEMENT: {
                    var tag = reader.getLocalName();

                    styler.popStyle(tag);

                    break;
                }
                case XMLStreamConstants.CHARACTERS:
                    if (reader.hasText()) {
                        collection.add(Pair.of(reader.getText(), styler.getStyle()));
                    }

                    break;
            }
        }

        return Optional.of(collection);
    }
}
