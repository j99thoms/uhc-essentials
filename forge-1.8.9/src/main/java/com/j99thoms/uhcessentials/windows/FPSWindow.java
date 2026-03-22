package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.BetterHUD;

public class FPSWindow extends BaseWindow {

    BetterHUD betterHUD;

    private int x = 2;
    private int y = 34;

    private int fillRed = 69;
    private int fillGreen = 69;
    private int fillBlue = 69;
    private int fillAlpha = 150;

    private int borderRed = 0;
    private int borderGreen = 0;
    private int borderBlue = 0;
    private int borderAlpha = 255;

    private int width = 0;
    private int height = 0;
    private int flash = 0;

    private float thickness = .8f;
    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager fileManager;
    private int nextFlash = 2;
    private boolean shouldFlash = true;
    private int count = 0;
    private int timer = 0;
    private int secondTimer = 0;
    private int fpsCount = 0;

    private Minecraft mc;
    private CoordinateWindow coordWindow;

    private double toggle = 1;

    public FPSWindow(BetterHUD betterHUD, Minecraft mc, CoordinateWindow coordWindow) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.coordWindow = coordWindow;
        fileManager = new FileManager("FPSWindow", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how slow your computer really is";
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
        fpsCount = Minecraft.getDebugFPS();
        String info = fpsCount + " FPS";
        width = mc.fontRendererObj.getStringWidth(info);
        betterHUD.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                coordWindow.getR(), coordWindow.getG(), coordWindow.getB(), coordWindow.getA(),
                coordWindow.getBorderR(), coordWindow.getBorderG(), coordWindow.getBorderB(), coordWindow.getBorderA(),
                coordWindow.getThickness());
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
    }

    @Override
    public int getR() {
        return 0;
    }

    @Override
    public int getG() {
        return 0;
    }

    @Override
    public int getB() {
        return 0;
    }

    @Override
    public int getA() {
        return 0;
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
    }

    @Override
    public int getBorderR() {
        return 0;
    }

    @Override
    public int getBorderG() {
        return 0;
    }

    @Override
    public int getBorderB() {
        return 0;
    }

    @Override
    public int getBorderA() {
        return 0;
    }

    @Override
    public void setThickness(float thickness) {
    }

    @Override
    public double getThickness() {
        return 0;
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
