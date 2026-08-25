package com.astrary.richtext;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLED = BUILDER
            .define("enabled", true);

    public static final ModConfigSpec.BooleanValue COMMAND_ENABLED = BUILDER
            .define("command_enabled", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
