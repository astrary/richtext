package com.astrary.richtext.text;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.phys.Vec2;

public final class CharFxInstance {
    public TextColor color;
    public Vec2 offset;
    private char chr;
    private int positionInCurrentSequence;

    public CharFxInstance(
        TextColor color,
        char chr,
        int positionInCurrentSequence
    ) {
        this.color = color;
        this.offset = Vec2.ZERO;
    }

    public char getCharacter() {
        return this.chr;
    }

    public int getCharacterPosition() {
        return this.positionInCurrentSequence;
    }

}
