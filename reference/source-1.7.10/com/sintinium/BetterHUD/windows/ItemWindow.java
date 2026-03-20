package com.sintinium.BetterHUD.windows;

import net.minecraft.client.gui.GuiScreen;

abstract class ItemWindow extends GuiScreen
{
	private boolean nothing;
    abstract void render();
}
