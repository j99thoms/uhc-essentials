package com.j99thoms.uhcessentials;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ForgeGuiContext implements GuiContext {

    private final Minecraft mc;

    public ForgeGuiContext(Minecraft mc) {
        this.mc = mc;
    }

    @Override
    public int getScreenWidth() {
        return new ScaledResolution(mc).getScaledWidth();
    }

    @Override
    public int getScreenHeight() {
        return new ScaledResolution(mc).getScaledHeight();
    }

    @Override
    public boolean isScreenOpen() {
        return mc.currentScreen != null;
    }

    @Override
    public int getMouseX() {
        return Mouse.getEventX() * getScreenWidth() / mc.displayWidth;
    }

    @Override
    public int getMouseY() {
        return getScreenHeight() - Mouse.getEventY() * getScreenHeight() / mc.displayHeight - 1;
    }

    @Override
    public boolean isMouseButtonDown(int button) {
        return Mouse.isButtonDown(button);
    }

    @Override
    public boolean getEventKeyState() {
        return Keyboard.getEventKeyState();
    }

    @Override
    public int getEventKey() {
        return Keyboard.getEventKey();
    }

    @Override
    public boolean isKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

    @Override
    public String getKeyName(int keyCode) {
        return Keyboard.getKeyName(keyCode);
    }
}
