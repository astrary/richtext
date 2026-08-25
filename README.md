# Rich Text

Rich replacement for Minecraft text formatting

![preview](https://cdn.modrinth.com/data/cached_images/e4e5428b8bc4fd6ce74c4413b9cc0950adf0f91d.gif)

## Basic Usage

Think of tags as containers: just wrap your text inside a style tag.

> `<b><rainbow>text goes here</rainbow></b>`

## Styles

Attributes definition: `name (type)`

| Name          |                              Attributes                               | Example                                                                      |
|---------------|:---------------------------------------------------------------------:|:-----------------------------------------------------------------------------|
| Bold          |                                   -                                   | `<b>bold text<b>`                                                            |
| Italic        |                                   -                                   | `<i>italic text</i>`                                                         |
| Strikethrough |                                   -                                   | `<s>strikethrough text</s>`                                                  |
| Underline     |                                   -                                   | `<u>underline text</u>`                                                      |
| Obfuscated    |                                   -                                   | `<o>obfuscated text</o>`                                                     |
| Color         |                            value (`color`)                            | `<color value="#CC44FF">colored text</color>`                                |
| Random Color  |                                   -                                   | `<rcolor>random color text</rcolor>`                                         |
| Shake         |                    ampX (`float`), ampY (`float`)                     | `<shake>shaking text</shake>`                                                |
| Pulse         |                   speed (`float`), scale (`float`)                    | `<pulse>pulsing text</pulse>`                                                |
| Rainbow       |                    freq (`float`), speed (`float`)                    | `<rainbow>rainbow text</rainbow>`                                            |
| Gradient      |     from (`color`), to (`color`), freq (`float`), speed (`float`)     | `<gradient speed="3.5" from="#3f2b96" to="#a8c0ff">gradient text</gradient>` |
| Wave          |            freq (`float`), amp (`float`), speed (`float`)             | `<wave>waving text</wave>`                                                   |
| Shine         | color (`color`), freq (`float`), threshold (`float`), speed (`float`) | `<shine color="#FF44EE">shining text</shine>`                                |