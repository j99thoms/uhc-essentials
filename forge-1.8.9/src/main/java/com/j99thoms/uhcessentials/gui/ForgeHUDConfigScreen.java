package com.j99thoms.uhcessentials.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import com.j99thoms.uhcessentials.GameContext;
import com.j99thoms.uhcessentials.GuiContext;
import com.j99thoms.uhcessentials.HUDGraphics;
import com.j99thoms.uhcessentials.windows.WindowManager;

public class ForgeHUDConfigScreen extends GuiScreen {

    private final Minecraft mc;
    private final HUDConfigScreen hudConfigScreen;
    private final ForgeOptionMenu forgeOptionMenu;

    public ForgeHUDConfigScreen(WindowManager windowManager, Minecraft mc, HUDGraphics hudGraphics,
            GuiContext guiContext, GameContext gameContext) {
        this.mc = mc;
        this.hudConfigScreen = new HUDConfigScreen(windowManager, hudGraphics, guiContext, gameContext);
        this.forgeOptionMenu = new ForgeOptionMenu(hudConfigScreen.getOptionMenu());
    }

    public void render() {
        ScreenRequest request = hudConfigScreen.render();
        handleRequest(request);
    }

    private void handleRequest(ScreenRequest request) {
        switch(request) {
            case OPEN_CONFIG:
                mc.displayGuiScreen(this);
                break;
            case OPEN_OPTIONS:
                mc.displayGuiScreen(null);
                mc.displayGuiScreen(forgeOptionMenu);
                break;
            case OPEN_COLORIZER:
                mc.displayGuiScreen(new ForgeColorizer(hudConfigScreen.getColorizer()));
                break;
            default:
                break;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
