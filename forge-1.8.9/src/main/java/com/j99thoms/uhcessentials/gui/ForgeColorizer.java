package com.j99thoms.uhcessentials.gui;

import net.minecraft.client.gui.GuiScreen;

public class ForgeColorizer extends GuiScreen {

    private final Colorizer colorizer;

    public ForgeColorizer(Colorizer colorizer) {
        this.colorizer = colorizer;
    }

    public void update() {
        colorizer.update();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
