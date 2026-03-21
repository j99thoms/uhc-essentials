package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Items;

import com.j99thoms.uhcessentials.BetterHUD;

public class CompassWindow extends BaseWindow {

    BetterHUD BH;

    private int x = 1;
    private int y = 68;

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

    public static double toggle = 1;

    private float thickness = .8f;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager FM;

    private Minecraft mc;

    public CompassWindow(BetterHUD BH, Minecraft mc) {
        this.mc = mc;
        this.BH = BH;
        FM = new FileManager("Compass", 3);
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
        BH.drawItemSprite(x, y, Items.compass, this);
        if ((int) toggle == 0)
            mc.fontRendererObj.drawStringWithShadow("X", x, y, 0xffffffff);
    }

    @Override
    public String getName() {
        return "Compass";
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
