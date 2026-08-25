package com.astrary.richtext.command;

import com.astrary.richtext.Config;
import com.astrary.richtext.RichTextMod;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = RichTextMod.MODID)
public class RichTextCommand {
    private static final int TEST_COUNT = 17;

    private static final LiteralArgumentBuilder<CommandSourceStack> COMMAND = Commands.literal("richtext")
        .then(Commands.literal("test")
            .then(
                Commands.literal("text")
                .executes(ctx -> {
                        var player = Minecraft.getInstance().player;
                        if (player == null) return 0;

                        Minecraft.getInstance().gui.getChat().clearMessages(false);

                        for (var i = 0; i < TEST_COUNT; i++) {
                            var text = Component.translatable(String.format("test.richtext.msg%d", i));
                            player.sendSystemMessage(text);
                        }

                        return Command.SINGLE_SUCCESS;
                    }
                )
            )
            .then(Commands.literal("item")
                .executes(ctx -> {
                        var player = Minecraft.getInstance().player;
                        if (player == null) return 0;

                        for (var i = 0; i < TEST_COUNT; i++) {
                            var text = Component.translatable(String.format("test.richtext.msg%d", i));
                            var item = new ItemStack(Items.NETHERITE_SWORD);
                            item.set(DataComponents.ITEM_NAME, text);

                            player.getInventory().add(item);
                        }

                        return Command.SINGLE_SUCCESS;
                    }
                )
            ).requires(source -> source.hasPermission(2))
        );

    @SubscribeEvent
    private static void registerCommands(RegisterCommandsEvent event) {
        if (Config.COMMAND_ENABLED.get())
            event.getDispatcher().register(COMMAND);
    }
}
