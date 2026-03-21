package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.BetterHUD;

public class FPSWindow extends BaseWindow {

    BetterHUD BH;

    private int x = 2;
    private int y = 34;

    private int r = 69;
    private int g = 69;
    private int b = 69;
    private int a = 150;

    private int r1 = 0;
    private int g1 = 0;
    private int b1 = 0;
    private int a1 = 255;

    private int width = 0;
    private int height = 0;
    private int flash = 0;

    private float thickness = .8f;
    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager FM;
    private int nextFlash = 2;
    private boolean shouldFlash = true;
    private int count = 0;
    private int timer = 0;
    private int secondTimer = 0;
    private int fpsCount = 0;

    private Minecraft mc;
    private CoordinateWindow CW;

    private double toggle = 1;

    public FPSWindow(BetterHUD BH, Minecraft mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.CW = CW;
        FM = new FileManager("FPSWindow", 3);
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
        BH.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                CW.getR(), CW.getG(), CW.getB(), CW.getA(),
                CW.getBorderR(), CW.getBorderG(), CW.getBorderB(), CW.getBorderA(), CW.getThickness());
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
    public void setRGBA(int r, int g, int b, int a) {
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
    public void setBorderRGB(int r, int g, int b, int a) {
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
    public void setThickness(float t) {
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
        FM.setArray(data);
    }

    @Override
    public void load() {
        data.clear();
        data = FM.getArray();
        if (data.size() < 3) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            toggle = data.get(2).intValue();
        }
    }
}
