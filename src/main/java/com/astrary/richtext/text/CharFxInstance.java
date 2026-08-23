package com.astrary.richtext.text;

import net.minecraft.network.chat.TextColor;
import net.minecraft.world.phys.Vec2;

public final class CharFxInstance {
    public TextColor color;
    public Vec2 offset;
    public float scale;

    private final char chr;
    private final int positionInCurrentSequence;

    public CharFxInstance(
        TextColor color,
        char chr,
        int positionInCurrentSequence
    ) {
        this.color = color;
        this.offset = Vec2.ZERO;
        this.scale = 1.0f;

        this.chr = chr;
        this.positionInCurrentSequence = positionInCurrentSequence;
    }

    public char getCharacter() {
        return this.chr;
    }

    public int getCharacterPosition() {
        return this.positionInCurrentSequence;
    }

    public boolean isScaled() {
        return this.scale != 1.0f;
    }
}
