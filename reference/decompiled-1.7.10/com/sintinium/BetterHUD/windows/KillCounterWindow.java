/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  bbu
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;

public class KillCounterWindow
extends DefaultWindow {
    private BetterHUD BH;
    private bbu FR;
    private bao mc;
    private CoordinateWindow CW;
    private int x = 3;
    private int y = 35;
    private int r = 69;
    private int g = 69;
    private int b = 69;
    private int a = 150;
    private int r1 = 0;
    private int g1 = 0;
    private int b1 = 0;
    private int a1 = 255;
    private String direction;
    private String xEx;
    private String zEx;
    private double toggle = 1.0;
    private int width;
    private int height = 10;
    private double thickness = 0.8f;
    float scale = 0.75f;
    public static int kills;
    private FileManager FM;
    private ArrayList<Double> data = new ArrayList();

    public KillCounterWindow(BetterHUD BH, bbu FR, bao mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.FR = FR;
        this.CW = CW;
        this.FM = new FileManager("KillCounter", 2);
        this.load();
    }

    @Override
    public String getToolTip() {
        return "Not implimented, how did you get it?!?";
    }

    @Override
    public void setToDefault() {
        this.setX(3);
        this.setY(35);
        this.save();
    }

    @Override
    public void update() {
        this.r = this.CW.getR();
        this.g = this.CW.getG();
        this.b = this.CW.getB();
        this.a = this.CW.getA();
        this.r1 = this.CW.getBorderR();
        this.g1 = this.CW.getBorderG();
        this.b1 = this.CW.getBorderB();
        this.a1 = this.CW.getBorderA();
        this.thickness = this.CW.getThickness();
    }

    @Override
    public void render() {
        String text = "Kills: " + kills;
        this.width = this.mc.l.a(text) + 10;
        this.BH.drawHUDRectWithBorder(this.x - 2, this.y - 1, this.width, this.height, this.r, this.g, this.b, this.a, this.r1, this.g1, this.b1, this.a1, this.thickness);
        this.mc.l.a("Kills: " + kills, this.x, this.y, -1);
        if (this.toggle == 0.0) {
            this.mc.l.b("X", this.x - 5, this.y - 5, -1);
        }
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
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public void setRGBA(int r, int g, int b, int a) {
    }

    @Override
    public int getR() {
        return this.r;
    }

    @Override
    public int getG() {
        return this.g;
    }

    @Override
    public int getB() {
        return this.b;
    }

    @Override
    public int getA() {
        return this.a;
    }

    @Override
    public void setBorderRGB(int r, int g, int b, int a) {
    }

    @Override
    public int getBorderR() {
        return this.r1;
    }

    @Override
    public int getBorderG() {
        return this.g1;
    }

    @Override
    public int getBorderB() {
        return this.b1;
    }

    @Override
    public int getBorderA() {
        return this.a1;
    }

    @Override
    public void setThickness(float t) {
    }

    @Override
    public double getThickness() {
        return this.thickness;
    }

    @Override
    public void save() {
        this.data.clear();
        this.data.add(Double.valueOf(this.x));
        this.data.add(Double.valueOf(this.y));
        this.data.add(this.toggle);
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
            this.toggle = this.data.get(2);
        }
    }

    @Override
    public String getName() {
        return "kills";
    }

    @Override
    public void toggle() {
        this.toggle = this.toggle == 1.0 ? 0.0 : 1.0;
    }

    @Override
    public int getToggled() {
        return (int)this.toggle;
    }
}
