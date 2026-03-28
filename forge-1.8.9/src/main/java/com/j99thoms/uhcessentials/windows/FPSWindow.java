package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.HUDGraphics;

public class FPSWindow extends ThemedWindow {

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 34;

    private int x = DEFAULT_X;
    private int y = DEFAULT_Y;

    private int width = 0;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final Minecraft mc;

    private double toggle = 1;

    public FPSWindow(HUDGraphics hudGraphics, Minecraft mc, WindowTheme theme) {
        super(hudGraphics, theme);
        this.mc = mc;
        fileManager = new FileManager("FPSWindow", 3);
        load();
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
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
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
        width = hudGraphics.getStringWidth(info);
        hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                theme.getThickness());
        hudGraphics.drawShadowedFont(info, x, y, 0xffffffff);

        if (getToggled() == 0) {
            hudGraphics.drawShadowedFont("X", x - 5, y - 5, 0xffffffff);
        }
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
