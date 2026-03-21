/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  apx
 *  bao
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;

public class EntityWindow
extends DefaultWindow {
    BetterHUD BH;
    private int x = 2;
    private int y = 58;
    private int r = 69;
    private int g = 69;
    private int b = 69;
    private int a = 150;
    private int r1 = 0;
    private int g1 = 0;
    private int b1 = 0;
    private int a1 = 255;
    private int width = 0;
    private int height = 12;
    private double toggle = 1.0;
    private float thickness = 0.8f;
    private ArrayList<Double> data = new ArrayList();
    private FileManager FM;
    private CoordinateWindow CW;
    private bao mc;

    public EntityWindow(BetterHUD BH, bao mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.CW = CW;
        this.FM = new FileManager("EntityWindow", 3);
        this.load();
    }

    @Override
    public String getToolTip() {
        return "Shows the entity count like F3 does";
    }

    @Override
    public void setToDefault() {
        this.setX(2);
        this.setY(58);
        this.save();
    }

    @Override
    public void toggle() {
        this.toggle = this.toggle == 0.0 ? 1.0 : 0.0;
    }

    @Override
    public int getToggled() {
        return (int)this.toggle;
    }

    @Override
    public void update() {
        int var22 = (int)Math.floor(this.mc.h.s);
        int var24 = (int)Math.floor(this.mc.h.u);
        apx var26 = this.mc.f.d(var22, var24);
        String[] entities = this.mc.r().split("/");
        String[] entities1 = entities[0].split(" ");
        String entity = "Entities: " + entities1[1];
        this.width = this.mc.l.a(entity);
        this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, this.mc.l.a(entity) + 2, this.getHeight(), this.CW.getR(), this.CW.getG(), this.CW.getB(), this.CW.getA(), this.CW.getBorderR(), this.CW.getBorderG(), this.CW.getBorderB(), this.CW.getBorderA(), this.CW.getThickness());
        this.mc.l.a(entity, this.x, this.y, -1);
        if ((int)this.toggle == 0) {
            this.mc.l.a("X", this.getX() - 5, this.getY() - 5, -1);
        }
    }

    @Override
    public String getName() {
        return "BiomeWindow";
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
        return this.width + 1;
    }

    @Override
    public int getHeight() {
        return 10;
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
}
