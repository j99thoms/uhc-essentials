/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;

public class ClockWindow
extends DefaultWindow {
    BetterHUD BH;
    private int x = 36;
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
    public static double toggle = 1.0;
    private float thickness = 0.8f;
    private ArrayList<Double> data = new ArrayList();
    private FileManager FM;
    private bao mc;

    public ClockWindow(BetterHUD BH, bao mc) {
        this.mc = mc;
        this.BH = BH;
        this.FM = new FileManager("Clock", 3);
        this.load();
    }

    @Override
    public String getToolTip() {
        return "I wonder if it's day out..";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0.0 ? 1.0 : 0.0;
    }

    @Override
    public void setToDefault() {
        this.setX(36);
        this.setY(68);
        this.save();
    }

    @Override
    public int getToggled() {
        return (int)toggle;
    }

    @Override
    public void update() {
        this.BH.drawItemSprite(this.x, this.y, 347, this);
        if ((int)toggle == 0) {
            this.mc.l.a("X", this.x, this.y, -1);
        }
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
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
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
        return 0.0;
    }

    @Override
    public int getWidth() {
        return 15;
    }

    @Override
    public int getHeight() {
        return 15;
    }

    @Override
    public void save() {
        this.data.clear();
        this.data.add(Double.valueOf(this.x));
        this.data.add(Double.valueOf(this.y));
        this.data.add(toggle);
        this.FM.setArray(this.data);
    }

    @Override
    public void load() {
        this.data.clear();
        this.data = this.FM.getArray();
        if (this.data.size() < 3) {
            this.save();
        } else {
            this.x = this.data.get(0).intValue();
            this.y = this.data.get(1).intValue();
            toggle = this.data.get(2).intValue();
        }
    }
}
