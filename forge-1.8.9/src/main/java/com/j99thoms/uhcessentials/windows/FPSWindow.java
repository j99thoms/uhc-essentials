package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.HUDGraphics;

public class FPSWindow extends ThemedWindow {

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 34;

    private int fps = 0;
    private String fpsInfo = fps + " FPS";
    private int width = 0;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    public FPSWindow(HUDGraphics hudGraphics, WindowTheme theme) {
        super(hudGraphics, theme);
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("FPSWindow", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how slow your computer really is`Right click to change colors";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
        fps = Minecraft.getDebugFPS();
        fpsInfo = fps + " FPS";
        width = hudGraphics.getStringWidth(fpsInfo);
    }

    @Override
    public void render() {
        hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, getWidth() + 2, getHeight() + 2,
                getR(), getG(), getB(), getA(),
                getBorderR(), getBorderG(), getBorderB(), getBorderA(),
                getThickness());
        hudGraphics.drawShadowedFont(fpsInfo, x, y, 0xffffffff);

        if (getToggled() == 0) {
            hudGraphics.drawShadowedFont("X", x - 5, y - 5, 0xffffffff);
        }
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
