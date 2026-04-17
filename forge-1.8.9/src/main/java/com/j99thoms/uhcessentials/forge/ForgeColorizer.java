package com.j99thoms.uhcessentials.forge;

import net.minecraft.client.gui.GuiScreen;

import com.j99thoms.uhcessentials.gui.Colorizer;

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
