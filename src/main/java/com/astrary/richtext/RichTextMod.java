package com.astrary.richtext;

import com.mojang.logging.LogUtils;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;

@Mod(RichTextMod.MODID)
@EventBusSubscriber(modid = RichTextMod.MODID)
public class RichTextMod {
    public static final String MODID = "richtext";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Lazy<KeyMapping> TEST_RICH_TEXT = Lazy.of(() -> new KeyMapping(
        "testRichText",
        GLFW.GLFW_KEY_G,
        "richtext"
    ));

    public RichTextMod(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    // TODO: remove before release
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(TEST_RICH_TEXT.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        while (TEST_RICH_TEXT.get().consumeClick()) {
            var player = Minecraft.getInstance().player;
            if (player == null) {
                continue;
            }
            Minecraft.getInstance().gui.getChat().clearMessages(false);

            player.sendSystemMessage(Component.literal(
                "<b>bold text</b>"
            ));
            player.sendSystemMessage(Component.literal(
                "<i>italic text</i>"
            ));
            player.sendSystemMessage(Component.literal(
                "<s>strikethrough text</s>"
            ));
            player.sendSystemMessage(Component.literal(
                "<u>underline text</u>"
            ));
            player.sendSystemMessage(Component.literal(
                "<o>obfuscated text</o>"
            ));
            player.sendSystemMessage(Component.literal(
                "<color value=\"#CC44FF\">colored text</color>"
            ));
            player.sendSystemMessage(Component.literal(
                "<rcolor>random color text</rcolor>"
            ));
            player.sendSystemMessage(Component.literal(
                "<shake>shaking text</shake>"
            ));
            player.sendSystemMessage(Component.literal(
                "<pulse>pulsing text</pulse>"
            ));
            player.sendSystemMessage(Component.literal(
                "<rainbow>rainbow text</rainbow>"
            ));
            player.sendSystemMessage(Component.literal(
                "<wave>waving text</wave>"
            ));

            player.sendSystemMessage(Component.literal(
                "<rainbow><u>rainbow</u> <u>text</u> <u>with</u> <u>underline</u></rainbow>"
            ));
            player.sendSystemMessage(Component.literal(
                "<color value=\"#DD0000\"><shake>red shaking text</shake></color>"
            ));
            player.sendSystemMessage(Component.literal(
                "<pulse><b><rcolor>pulsing bold random color text</rcolor></b></pulse>"
            ));
            player.sendSystemMessage(Component.literal(
                "<rainbow><pulse>rainbow pulsing text</pulse></rainbow>"
            ));
            player.sendSystemMessage(Component.empty());
            player.sendSystemMessage(Component.literal(
                "<wave amp=\"0.25\"><color value=\"#C4E0E5\">Rare</color></wave>")
            );
            player.sendSystemMessage(Component.literal(
                "<b><wave amp=\"0.5\"><color value=\"#DA22FF\">Epic</color></wave></b>")
            );
            player.sendSystemMessage(Component.literal(
                "<b><wave><gradient speed=\"3.5\" from=\"#F37335\" to=\"#FDC830\">Legendary</gradient></wave></b>"
            ));
            player.sendSystemMessage(Component.literal(
                "<b><wave amp=\"1.5\"><gradient speed=\"3.5\" from=\"#8E2DE2\" to=\"#4A00E0\">Mythic</gradient></wave></b>"
            ));
            player.sendSystemMessage(Component.literal(
                "<b><wave><rainbow>Something Cool</rainbow></wave></b>"
            ));
        }
    }
}
