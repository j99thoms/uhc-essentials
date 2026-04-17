package com.j99thoms.uhcessentials.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j99thoms.uhcessentials.api.GameContext;
import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.util.VersionChecker;
import com.j99thoms.uhcessentials.windows.WindowManager;

public class HUDEventHandler {

    private static final String VERSION_URL = "https://raw.githubusercontent.com/j99thoms/uhc-essentials/main/forge-1.8.9/version.txt";
    private static final Logger LOGGER = LogManager.getLogger(UHCEssentialsMod.MODID);
    private final Minecraft mc = Minecraft.getMinecraft();
    private WindowManager windowManager;
    private ForgeHUDConfigScreen hudConfigScreen;
    private boolean initialized = false;

    private void init() {
        HUDGraphics hudGraphics = new ForgeHUDGraphics(mc.fontRendererObj, mc);
        GameContext gameContext = new ForgeGameContext(mc);
        GuiContext guiContext = new ForgeGuiContext(mc);

        windowManager = new WindowManager(hudGraphics, gameContext);
        hudConfigScreen = new ForgeHUDConfigScreen(windowManager, mc, hudGraphics, guiContext, gameContext);

        initialized = true;
        LOGGER.info("UHC Essentials loaded successfully.");
    }

    public void checkVersion() {
        Consumer<String> notifier = message -> mc.thePlayer.addChatMessage(new ChatComponentText(message));
        new VersionChecker(VERSION_URL).check(notifier);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!initialized) {
            init();
            checkVersion();
        }

        windowManager.update();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (!initialized) return;

        windowManager.render();
        hudConfigScreen.render();
    }
}
