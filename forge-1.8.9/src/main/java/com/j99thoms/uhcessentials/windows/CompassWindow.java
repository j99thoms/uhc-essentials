package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;

import com.j99thoms.uhcessentials.BetterHUD;

public class CompassWindow extends BaseWindow {

    private final BetterHUD betterHUD;

    private int x = 1;
    private int y = 68;

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

    private double toggle = 1;

    private float thickness = .8f;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final Minecraft mc;

    public CompassWindow(BetterHUD betterHUD, Minecraft mc) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        fileManager = new FileManager("Compass", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Points to 0, 0 (same with compass item disable to fix normal compass)`/compass <x> <y> in chat to change direction";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public void setToDefault() {
        setX(1);
        setY(68);
        save();
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        betterHUD.drawItemSprite(x, y, Items.compass);
        if ((int) toggle == 0)
            mc.fontRendererObj.drawStringWithShadow("X", x, y, 0xffffffff);
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
        return 14;
    }

    @Override
    public int getHeight() {
        return 12;
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
