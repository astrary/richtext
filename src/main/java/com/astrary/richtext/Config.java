package com.astrary.richtext;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue COMMAND_ENABLED = BUILDER
            .define("command_enabled", false);

    public static final ModConfigSpec.BooleanValue VANILLA_ARGUMENT_FORMATTING_ENABLED = BUILDER
            .define("vanilla_argument_formatting_enabled", false);

    static final ModConfigSpec SPEC = BUILDER.build();
}
