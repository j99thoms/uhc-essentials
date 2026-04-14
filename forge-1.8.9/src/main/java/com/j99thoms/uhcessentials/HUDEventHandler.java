package com.j99thoms.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j99thoms.uhcessentials.windows.WindowManager;

public class HUDEventHandler {

    private static final Logger LOGGER = LogManager.getLogger(UHCEssentialsMod.MODID);
    private final Minecraft mc = Minecraft.getMinecraft();
    private WindowManager windowManager;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (windowManager == null) {
            LOGGER.info("UHC Essentials loaded successfully.");
            HUDGraphics hudGraphics = new ForgeHUDGraphics(mc.fontRendererObj, mc);
            windowManager = new WindowManager(hudGraphics, mc);
            new VersionChecker().check(mc);
        }

        windowManager.update();
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        if (windowManager == null) return;

        windowManager.render();
    }
}
