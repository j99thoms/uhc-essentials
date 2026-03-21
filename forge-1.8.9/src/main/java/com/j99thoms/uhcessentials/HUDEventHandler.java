package com.j99thoms.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HUDEventHandler {

    private static final Logger LOGGER = LogManager.getLogger(UHCEssentialsMod.MODID);
    private final Minecraft mc = Minecraft.getMinecraft();
    private boolean loaded = false;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (mc.thePlayer == null || mc.theWorld == null) return;

        if (!loaded) {
            LOGGER.info("UHC Essentials loaded successfully.");
            loaded = true;
        }
    }

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.type != RenderGameOverlayEvent.ElementType.ALL) return;
        // TODO: call betterHUD.render() once BetterHUD is wired up
    }
}
