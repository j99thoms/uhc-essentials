package com.j99thoms.uhcessentials.gui;

import net.minecraft.client.gui.GuiScreen;

public class ForgeOptionMenu extends GuiScreen {

    private final OptionMenu optionMenu;

    public ForgeOptionMenu(OptionMenu optionMenu) {
        this.optionMenu = optionMenu;
    }

    public void render() {
        optionMenu.render();
    }

    public void reset() {
        optionMenu.reset();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
