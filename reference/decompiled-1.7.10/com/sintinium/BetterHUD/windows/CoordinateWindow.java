/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  bbu
 *  org.lwjgl.opengl.GL11
 *  p
 *  qh
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class CoordinateWindow
extends DefaultWindow {
    private BetterHUD BH;
    private bbu FR;
    private bao mc;
    private int x = 2;
    private int y = 2;
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
    private int height = 30;
    private double thickness = 0.8f;
    float scale = 0.75f;
    private FileManager FM;
    private ArrayList<Double> data = new ArrayList();

    public CoordinateWindow(BetterHUD BH, bbu FR, bao mc) {
        this.mc = mc;
        this.BH = BH;
        this.FR = FR;
        this.FM = new FileManager("Coord", 12);
        this.load();
    }

    @Override
    public String getToolTip() {
        return "Shows your position right click to change the color of everything!`And right click again to close out of the colorizer";
    }

    @Override
    public void toggle() {
        this.toggle = this.toggle == 0.0 ? 1.0 : 0.0;
    }

    @Override
    public void setToDefault() {
        this.setX(2);
        this.setY(2);
        this.save();
    }

    @Override
    public int getToggled() {
        return (int)this.toggle;
    }

    @Override
    public String getName() {
        return "Coordinate";
    }

    @Override
    public void update() {
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
    public void save() {
        this.data.clear();
        this.data.add(Double.valueOf(this.x));
        this.data.add(Double.valueOf(this.y));
        this.data.add(Double.valueOf(this.r));
        this.data.add(Double.valueOf(this.g));
        this.data.add(Double.valueOf(this.b));
        this.data.add(Double.valueOf(this.a));
        this.data.add(Double.valueOf(this.r1));
        this.data.add(Double.valueOf(this.g1));
        this.data.add(Double.valueOf(this.b1));
        this.data.add(Double.valueOf(this.a1));
        this.data.add(this.thickness);
        this.data.add(this.toggle);
        this.FM.setArray(this.data);
    }

    @Override
    public void load() {
        this.data.clear();
        this.data = this.FM.getArray();
        if (this.data.size() < 12) {
            this.save();
        } else {
            this.x = this.data.get(0).intValue();
            this.y = this.data.get(1).intValue();
            this.r = this.data.get(2).intValue();
            this.g = this.data.get(3).intValue();
            this.b = this.data.get(4).intValue();
            this.a = this.data.get(5).intValue();
            this.r1 = this.data.get(6).intValue();
            this.g1 = this.data.get(7).intValue();
            this.b1 = this.data.get(8).intValue();
            this.a1 = this.data.get(9).intValue();
            this.thickness = this.data.get(10);
            this.toggle = this.data.get(11);
        }
    }

    @Override
    public void render() {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        this.drawCoordPane();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        if (this.getToggled() == 0) {
            this.BH.drawShadowedFont("X", this.getX() - 3, this.getY() - 3, 0xFFFFFF);
        }
    }

    private void drawCoordPane() {
        double x = Math.floor(this.mc.h.s);
        double y = Math.floor(this.mc.h.t - 1.0);
        double z = Math.floor(this.mc.h.u);
        int var25 = qh.c((double)((double)(this.mc.h.y * 4.0f / 360.0f) + 0.5)) & 3;
        String direction = p.c[var25];
        double rotation = qh.g((float)this.mc.h.y);
        direction = direction.equalsIgnoreCase("north") ? direction + " -Z" : (direction.equalsIgnoreCase("east") ? direction + " +X" : (direction.equalsIgnoreCase("south") ? direction + " +Z" : direction + " -X"));
        int width = x < 1000.0 && y < 1000.0 && z < 1000.0 && x > -1000.0 && y > -1000.0 && z > -1000.0 ? 54 : (x > -10000.0 && y > -10000.0 & z > -10000.0 && x < 10000.0 && y < 10000.0 && z < 10000.0 ? 60 : (x > -100000.0 && y > -100000.0 && z > -100000.0 && x < 100000.0 && y < 100000.0 && z < 100000.0 ? 66 : (x > -1000000.0 && y > -1000000.0 && z > -1000000.0 && x < 1000000.0 && y < 1000000.0 && z < 1000000.0 ? 72 : (x > -1.0E7 && y > -1.0E7 && z > -1.0E7 && x < 1.0E7 && y < 1.0E7 && z < 1.0E7 ? 78 : (x > -1.0E8 && y > -1.0E8 && z > -1.0E8 && x < 1.0E8 && y < 1.0E8 && z < 1.0E8 ? 98 : 86)))));
        this.width = width;
        this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, width, this.height, this.r, this.g, this.b, this.a, this.r1, this.g1, this.b1, this.a1, this.thickness);
        GL11.glEnable((int)3553);
        if (rotation > -22.5 && rotation <= 22.5) {
            this.direction = "S";
            this.zEx = "+";
        } else if (rotation > 22.5 && rotation <= 67.5) {
            this.direction = "SW";
            this.zEx = "+";
            this.xEx = "-";
        } else if (rotation > 67.5 && rotation <= 112.5) {
            this.direction = "W";
            this.xEx = "-";
        } else if (rotation > 112.5 && rotation <= 157.5) {
            this.direction = "NW";
            this.zEx = "-";
            this.xEx = "-";
        } else if (rotation > 157.5 && rotation <= 202.5 || rotation > -180.0 && rotation <= -157.5) {
            this.direction = "N";
            this.zEx = "-";
        } else if (rotation > -157.5 && rotation <= -112.5) {
            this.direction = "NE";
            this.zEx = "-";
            this.xEx = "+";
        } else if (rotation > -112.5 && rotation <= -67.5) {
            this.direction = "E";
            this.xEx = "+";
        } else if (rotation > -67.5 && rotation <= -22.5) {
            this.direction = "SE";
            this.zEx = "+";
            this.xEx = "+";
        }
        this.b(this.FR, "X: " + (int)x, this.x, this.y, 0xFFFFFF);
        this.b(this.FR, "Y: " + (int)y, this.x, this.y + 10, 0xFFFFFF);
        this.b(this.FR, "Z: " + (int)z, this.x, this.y + 20, 0xFFFFFF);
        int tempx = this.getX() + this.getWidth() - 12;
        int dLength = this.mc.l.a(this.direction) / 2;
        this.b(this.FR, this.xEx, tempx, this.y, 0xFFFFFF);
        this.b(this.FR, this.direction, tempx - dLength + 3, this.y + 10, 0xFFFFFF);
        this.b(this.FR, this.zEx, tempx, this.y + 20, 0xFFFFFF);
        GL11.glDisable((int)3553);
        this.xEx = "";
        this.zEx = "";
    }

    @Override
    public void setRGBA(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
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
        this.r1 = r;
        this.g1 = g;
        this.b1 = b;
        this.a1 = a;
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
        this.thickness = t;
    }

    @Override
    public double getThickness() {
        return this.thickness;
    }
}
