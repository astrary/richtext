# Rich Text

Rich replacement for Minecraft text formatting

<img width="527" height="318" alt="java_3T6gn1Blsi" src="https://github.com/user-attachments/assets/8dacec76-a115-4f0b-9cd3-928ba5e89ac4" />

## Styles

Parameters definition: `name (type)`

| Name          |                              Attributes                               | Example                                       |
|---------------|:---------------------------------------------------------------------:|:----------------------------------------------|
| Bold          |                                   -                                   | `<b>bold text<b>`                             |
| Italic        |                                   -                                   | `<i>italic text</i>`                          |
| Strikethrough |                                   -                                   | `<s>strikethrough text</s>`                   |
| Underline     |                                   -                                   | `<u>underline text</u>`                       |
| Obfuscated    |                                   -                                   | `<o>obfuscated text</o>`                      |
| Color         |                            value (`color`)                            | `<color value="#CC44FF">colored text</color>` |
| Random Color  |                                   -                                   | `<rcolor>random color text</rcolor>`          |
| Shake         |                    ampX (`float`), ampY (`float`)                     | `<shake>shaking text</shake>`                 |
| Pulse         |                   speed (`float`), scale (`float`)                    | `<pulse>pulsing text</pulse>`                 |
| Rainbow       |                 freq (`float`), waveLength (`float`)                  | `<rainbow>rainbow text</rainbow>`             |
| Wave          |            freq (`float`), amp (`float`), speed (`float`)             | `<wave>waving text</wave>`                    |
| Shine         | color (`color`), freq (`float`), threshold (`float`), speed (`float`) | `<shine color="#FF44EE">shining text</shine>` |

## Combining styles

#### Red shaking text
> `<color value="#DD0000"><shake>red shaking text</shake></color>`

#### Pulsing bold randomly colored text
> `<pulse><b><rcolor>pulsing bold random color text</rcolor></b></pulse>`

#### Rainbow pulsing text
> `<rainbow><pulse>rainbow pulsing text</pulse></rainbow>`

#### Waving gradient text
> `<b><wave><gradient speed="3.5" from="#3f2b96" to="#a8c0ff">waving gradient text</gradient></wave></b>`
