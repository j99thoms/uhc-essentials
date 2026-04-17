package com.j99thoms.uhcessentials.forge;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j99thoms.uhcessentials.api.GameContext;
import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.windows.WindowManager;

public class HUDEventHandler {

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

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!initialized) {
            init();
            new VersionChecker().check(mc);
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
