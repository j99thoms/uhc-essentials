/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  bca
 *  bdw
 *  org.lwjgl.input.Mouse
 *  org.lwjgl.opengl.GL11
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Colorizer
extends bdw {
    private BetterHUD BH;
    private DefaultWindow DW;
    private bao mc;
    private int r;
    private int g;
    private int b;
    private int a;
    private int r1;
    private int g1;
    private int b1;
    private int a1;
    private float thickness;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private int lastX;
    private int lastY;
    private int rx;
    private int ry;
    private int gx;
    private int gy;
    private int bx;
    private int by;
    private int ax;
    private int ay;
    private int tx;
    private int ty;
    private boolean grabbedr = false;
    private boolean grabbedg = false;
    private boolean grabbedb = false;
    private boolean grabbeda = false;
    private boolean grabbedthickness = false;
    private boolean grabbedthick = false;
    private boolean border = false;
    private int cooldown = 0;
    private boolean isCooldown = false;

    public Colorizer(BetterHUD BH, DefaultWindow DW, bao mc) {
        this.BH = BH;
        this.DW = DW;
        this.mc = mc;
        this.getInts();
        this.thickness = (float)DW.getThickness();
        this.mc.a((bdw)this);
    }

    public void update() {
        this.getInts();
        this.mouse();
        this.render();
    }

    private void getInts() {
        this.r = this.DW.getR();
        this.g = this.DW.getG();
        this.b = this.DW.getB();
        this.a = this.DW.getA();
        this.r1 = this.DW.getBorderR();
        this.g1 = this.DW.getBorderG();
        this.b1 = this.DW.getBorderB();
        this.a1 = this.DW.getBorderA();
        this.thickness = (float)this.DW.getThickness();
    }

    private void mouse() {
        if (this.isCooldown) {
            ++this.cooldown;
        }
        if (this.cooldown % 2 == 0 && this.isCooldown) {
            this.isCooldown = false;
        }
        if (Mouse.isButtonDown((int)0)) {
            bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
            this.x = Mouse.getX();
            this.y = Mouse.getY();
            this.x = Mouse.getEventX() * this.l / this.mc.d;
            this.y = this.m - Mouse.getEventY() * this.m / this.mc.e - 1;
            this.dx = this.x - this.lastX;
            this.dy = this.y - this.lastY;
            this.lastX = this.x;
            this.lastY = this.y;
            if (!this.border) {
                this.r = this.r <= 0 ? 0 : this.r;
                this.r = this.r > 255 ? 255 : this.r;
                this.g = this.g <= 0 ? 0 : this.g;
                this.g = this.g > 255 ? 255 : this.g;
                this.b = this.b <= 0 ? 0 : this.b;
                this.b = this.b > 255 ? 255 : this.b;
                this.a = this.a < 0 ? 0 : this.a;
                int n = this.a = this.a > 255 ? 255 : this.a;
                if (this.y < this.ry + 7 && this.y >= this.ry && this.x < this.rx + 5 && this.x >= this.rx || this.grabbedr) {
                    this.grabbedr = true;
                    this.DW.setRGBA(this.r + this.dx, this.g, this.b, this.a);
                } else if (this.y < this.gy + 7 && this.y >= this.gy && this.x < this.gx + 5 && this.x >= this.gx || this.grabbedg) {
                    this.grabbedg = true;
                    this.DW.setRGBA(this.r, this.g + this.dx, this.b, this.a);
                } else if (this.y < this.by + 7 && this.y >= this.by && this.x < this.bx + 5 && this.x >= this.bx || this.grabbedb) {
                    this.grabbedb = true;
                    this.DW.setRGBA(this.r, this.g, this.b + this.dx, this.a);
                } else if (this.y < this.ay + 7 && this.y >= this.ay && this.x < this.ax + 5 && this.x >= this.ax || this.grabbeda) {
                    this.grabbeda = true;
                    if (!this.border) {
                        this.DW.setRGBA(this.r, this.g, this.b, this.a + this.dx);
                    }
                } else if (this.x <= var1.a() / 2 + 150 + 12 && this.x >= var1.a() / 2 + 150 && this.y <= var1.b() / 2 - 25 + 60 + 9 && this.y >= var1.b() / 2 - 25 + 60) {
                    this.border = false;
                } else if (this.x <= var1.a() / 2 + 150 + 12 + 21 && this.x >= var1.a() / 2 + 150 && this.y <= var1.b() / 2 - 25 + 70 + 9 && this.y >= var1.b() / 2 - 25 + 70) {
                    this.border = true;
                }
            } else {
                this.r1 = this.r1 <= 0 ? 0 : this.r1;
                this.r1 = this.r1 > 255 ? 255 : this.r1;
                this.g1 = this.g1 <= 0 ? 0 : this.g1;
                this.g1 = this.g1 > 255 ? 255 : this.g1;
                this.b1 = this.b1 <= 0 ? 0 : this.b1;
                this.b1 = this.b1 > 255 ? 255 : this.b1;
                this.a1 = this.a1 <= 0 ? 0 : this.a1;
                this.a1 = this.a1 > 255 ? 255 : this.a1;
                this.thickness = this.thickness <= 0.0f ? 0.0f : this.thickness;
                float f = this.thickness = this.thickness > 255.0f ? 255.0f : this.thickness;
                if (this.y < this.ry + 7 && this.y >= this.ry && this.x < this.rx + 5 && this.x >= this.rx || this.grabbedr) {
                    this.grabbedr = true;
                    this.DW.setBorderRGB(this.r1 + this.dx, this.g1, this.b1, this.a1);
                } else if (this.y < this.gy + 7 && this.y >= this.gy && this.x < this.gx + 5 && this.x >= this.gx || this.grabbedg) {
                    this.grabbedg = true;
                    this.DW.setBorderRGB(this.r1, this.g1 + this.dx, this.b1, this.a1);
                } else if (this.y < this.by + 7 && this.y >= this.by && this.x < this.bx + 5 && this.x >= this.bx || this.grabbedb) {
                    this.grabbedb = true;
                    this.DW.setBorderRGB(this.r1, this.g1, this.b1 + this.dx, this.a1);
                } else if (this.y < this.ay + 7 && this.y >= this.ay && this.x < this.ax + 5 && this.x >= this.ax || this.grabbeda) {
                    this.grabbeda = true;
                    this.DW.setBorderRGB(this.r1, this.g1, this.b1, this.a1 + this.dx);
                } else if (this.y < this.ty + 7 && this.y >= this.ty && this.x < this.tx + 5 && this.x >= this.tx || this.grabbedthickness) {
                    this.DW.setThickness(this.thickness / 255.0f + (float)(this.dx / 255));
                    this.grabbedthickness = true;
                } else if (this.x <= var1.a() / 2 + 150 + 12 && this.x >= var1.a() / 2 + 150 && this.y <= var1.b() / 2 - 25 + 60 + 9 && this.y >= var1.b() / 2 - 25 + 60) {
                    this.border = false;
                } else if (this.x <= var1.a() / 2 + 150 + 12 + 21 && this.x >= var1.a() / 2 + 150 && this.y <= var1.b() / 2 - 25 + 70 + 9 && this.y >= var1.b() / 2 - 25 + 70) {
                    this.border = true;
                }
            }
            this.DW.setThickness(0.5f);
            this.DW.save();
        } else {
            this.cooldown = 0;
            this.grabbedr = false;
            this.grabbedg = false;
            this.grabbedb = false;
            this.grabbeda = false;
            this.grabbedthickness = false;
            this.lastX = Mouse.getEventX() * this.l / this.mc.d;
            this.lastY = this.m - Mouse.getEventY() * this.m / this.mc.e - 1;
        }
    }

    private void render() {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
        if (!this.border) {
            this.rx = var1.a() / 2 - 127 + this.r;
            this.ry = var1.b() / 2 - 2 - 32;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 - 32, "Red");
            this.renderKnob(this.rx, this.ry, "red");
            this.gx = var1.a() / 2 - 127 + this.g;
            this.gy = var1.b() / 2 - 2;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2, "Green");
            this.renderKnob(this.gx, this.gy, "green");
            this.bx = var1.a() / 2 - 127 + this.b;
            this.by = var1.b() / 2 - 2 + 32;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 + 32, "Blue");
            this.renderKnob(this.bx, this.by, "blue");
            this.ax = var1.a() / 2 - 127 + this.a;
            this.ay = var1.b() / 2 - 2 + 64;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 + 64, "Alpha");
            this.renderKnob(this.ax, this.ay, "alpha");
        } else {
            this.rx = var1.a() / 2 - 127 + this.r1;
            this.ry = var1.b() / 2 - 2 - 32;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 - 32, "Red");
            this.renderKnob(this.rx, this.ry, "red");
            this.gx = var1.a() / 2 - 127 + this.g1;
            this.gy = var1.b() / 2 - 2;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2, "Green");
            this.renderKnob(this.gx, this.gy, "green");
            this.bx = var1.a() / 2 - 127 + this.b1;
            this.by = var1.b() / 2 - 2 + 32;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 + 32, "Blue");
            this.renderKnob(this.bx, this.by, "blue");
            this.ax = var1.a() / 2 - 127 + this.a1;
            this.ay = var1.b() / 2 - 2 + 64;
            this.renderSlider(var1.a() / 2 - 127, var1.b() / 2 - 2 + 64, "Alpha");
            this.renderKnob(this.ax, this.ay, "alpha");
        }
        this.BH.drawHUDRectWithBorder(var1.a() / 2 + 150, var1.b() / 2 - 25, 50.0, 50.0, this.r, this.g, this.b, this.a, this.r1, this.g1, this.b1, this.a1, this.DW.getThickness());
        int x = var1.a() / 2 + 150 - 1;
        int y = var1.b() / 2 - 25 + 60;
        int width = 13;
        int height = 9;
        if (!this.border) {
            this.BH.drawHUDRectWithBorder(x, y, width, height, 120.0, 120.0, 120.0, 150.0, 0.0, 0.0, 0.0, 255.0, 0.5);
            this.BH.drawHUDRectWithBorder(x, y + 10, width + 21, height, 69.0, 69.0, 69.0, 150.0, 0.0, 0.0, 0.0, 255.0, 0.2f);
        } else {
            this.BH.drawHUDRectWithBorder(x, y + 10, width + 21, height, 120.0, 120.0, 120.0, 150.0, 0.0, 0.0, 0.0, 255.0, 0.5);
            this.BH.drawHUDRectWithBorder(x, y, width, height, 69.0, 69.0, 69.0, 150.0, 0.0, 0.0, 0.0, 255.0, 0.2f);
        }
        this.BH.b(this.mc.l, "BG", var1.a() / 2 + 150, var1.b() / 2 - 25 + 60, 0xFFFFFF);
        this.BH.b(this.mc.l, "Outline", var1.a() / 2 + 150, var1.b() / 2 - 25 + 70, 0xFFFFFF);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    private void renderSlider(int x, int y, String effector) {
        if (this.r > 255 || this.r < 0 || this.g > 255 || this.g < 0 || this.b > 255 || this.b < 0 || this.a > 255 || this.a < 0 || this.r1 > 255 || this.r1 < 0 || this.g1 > 255 || this.g1 < 0 || this.b1 > 255 || this.b1 < 0 || this.a1 > 255 || this.a1 < 0) {
            this.DW.setRGBA(Math.abs(this.r <= 255 ? this.r : 255), Math.abs(this.g <= 255 ? this.g : 255), Math.abs(this.b <= 255 ? this.b : 255), Math.abs(this.a <= 255 ? this.a : 255));
            this.DW.setBorderRGB(Math.abs(this.r1 <= 255 ? this.r1 : 255), Math.abs(this.g1 <= 255 ? this.g1 : 255), Math.abs(this.b1 <= 255 ? this.b1 : 255), Math.abs(this.a1 <= 255 ? this.a1 : 255));
            this.getInts();
        }
        this.BH.drawHUDRectWithBorder(x, y, 255.0, 5.0, 69.0, 69.0, 69.0, 180.0, 0.0, 0.0, 0.0, 255.0, 0.5);
        GL11.glEnable((int)3553);
        y -= 10;
        if (!this.border) {
            if (effector.equalsIgnoreCase("Red")) {
                this.BH.b(this.mc.l, effector + ": " + this.r, x, y, -1);
            } else if (effector.equalsIgnoreCase("Green")) {
                this.BH.b(this.mc.l, effector + ": " + this.g, x, y, -1);
            } else if (effector.equalsIgnoreCase("Blue")) {
                this.BH.b(this.mc.l, effector + ": " + this.b, x, y, -1);
            } else if (effector.equalsIgnoreCase("Alpha")) {
                this.BH.b(this.mc.l, effector + ": " + this.a, x, y, -1);
            }
        } else if (effector.equalsIgnoreCase("Red")) {
            this.BH.b(this.mc.l, effector + ": " + this.r1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Green")) {
            this.BH.b(this.mc.l, effector + ": " + this.g1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Blue")) {
            this.BH.b(this.mc.l, effector + ": " + this.b1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Alpha")) {
            this.BH.b(this.mc.l, effector + ": " + this.a1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Thickness")) {
            this.BH.b(this.mc.l, effector + ": " + this.thickness / 255.0f, x, y, -1);
        }
    }

    private void renderKnob(int x, int y, String color) {
        if (!this.border) {
            if (color.equalsIgnoreCase("red")) {
                this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 255.0, 0.0, 0.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
            } else if (color.equalsIgnoreCase("green")) {
                this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 255.0, 0.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
            } else if (color.equalsIgnoreCase("blue")) {
                this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 0.0, 255.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
            } else if (color.equalsIgnoreCase("alpha")) {
                this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 255.0, 255.0, 255.0, 100.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
            } else if (color.equalsIgnoreCase("thickness")) {
                this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0);
            }
        } else if (color.equalsIgnoreCase("red")) {
            this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 255.0, 0.0, 0.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
        } else if (color.equalsIgnoreCase("green")) {
            this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 255.0, 0.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
        } else if (color.equalsIgnoreCase("blue")) {
            this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 0.0, 255.0, 255.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
        } else if (color.equalsIgnoreCase("alpha")) {
            this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 255.0, 255.0, 255.0, 100.0, 0.0, 0.0, 0.0, 255.0, 0.3f);
        } else if (color.equalsIgnoreCase("thickness")) {
            this.BH.drawHUDRectWithBorder(x, y - 1, 2.0, 7.0, 0.0, 0.0, 0.0, 100.0, 255.0, 255.0, 255.0, 255.0, 0.3f);
        }
    }
}
