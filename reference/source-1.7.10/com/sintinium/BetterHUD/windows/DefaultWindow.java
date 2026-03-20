package com.sintinium.BetterHUD.windows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public abstract class DefaultWindow extends GuiScreen {
	
	public abstract void update();

	public abstract void render();
	
	public abstract void setToDefault();

	public abstract void setX(int x);

	public abstract void setY(int y);

	public abstract int getX();

	public abstract int getY();

	public abstract int getWidth();

	public abstract int getHeight();

	public abstract void setRGBA(int r, int g, int b, int a);

	public abstract int getR();

	public abstract int getG();

	public abstract int getB();

	public abstract int getA();

	public abstract void setBorderRGB(int r, int g, int b, int a);

	public abstract int getBorderR();

	public abstract int getBorderG();

	public abstract int getBorderB();

	public abstract int getBorderA();

	public abstract void setThickness(float t);

	public abstract double getThickness();

	public abstract void save();

	public abstract void load();
	
	public abstract String getName();
	
	public abstract void toggle();
	public abstract int getToggled();
}
