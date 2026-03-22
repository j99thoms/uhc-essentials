package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.BetterHUD;

public class FPSWindow extends BaseWindow {

    BetterHUD betterHUD;

    private int x = 2;
    private int y = 34;

    private int width = 0;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager fileManager;
    private WindowTheme theme;

    private Minecraft mc;

    private double toggle = 1;

    public FPSWindow(BetterHUD betterHUD, Minecraft mc, WindowTheme theme) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.theme = theme;
        fileManager = new FileManager("FPSWindow", 3);
        load();
    }

    @Override
    public boolean isThemed() {
        return true;
    }

    @Override
    public String getToolTip() {
        return "Shows how slow your computer really is`Right click to change colors";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public void setToDefault() {
        setX(2);
        setY(34);
        save();
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        int fpsCount = Minecraft.getDebugFPS();
        String info = fpsCount + " FPS";
        width = mc.fontRendererObj.getStringWidth(info);
        betterHUD.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                theme.getThickness());
        mc.fontRendererObj.drawStringWithShadow(info, x, y, 0xffffffff);

        if (toggle == 0) {
            mc.fontRendererObj.drawStringWithShadow("X", x - 5, y - 5, 0xffffffff);
        }
    }

    @Override
    public String getName() {
        return "FPSWindow";
    }

    @Override
    public void render() {
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setRGBA(int red, int green, int blue, int alpha) {
        theme.setRGBA(red, green, blue, alpha);
    }

    @Override
    public int getR() {
        return theme.getR();
    }

    @Override
    public int getG() {
        return theme.getG();
    }

    @Override
    public int getB() {
        return theme.getB();
    }

    @Override
    public int getA() {
        return theme.getA();
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
        theme.setBorderRGB(red, green, blue, alpha);
    }

    @Override
    public int getBorderR() {
        return theme.getBorderR();
    }

    @Override
    public int getBorderG() {
        return theme.getBorderG();
    }

    @Override
    public int getBorderB() {
        return theme.getBorderB();
    }

    @Override
    public int getBorderA() {
        return theme.getBorderA();
    }

    @Override
    public void setThickness(float thickness) {
        theme.setThickness(thickness);
    }

    @Override
    public double getThickness() {
        return theme.getThickness();
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) toggle);
        fileManager.setArray(data);
        theme.save();
    }

    @Override
    public void load() {
        data.clear();
        data = fileManager.getArray();
        if (data.size() < 3) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            toggle = data.get(2).intValue();
        }
    }
}
