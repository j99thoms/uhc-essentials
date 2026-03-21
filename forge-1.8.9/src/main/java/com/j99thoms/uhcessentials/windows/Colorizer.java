package com.j99thoms.uhcessentials.windows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Mouse;

import com.j99thoms.uhcessentials.BetterHUD;

public class Colorizer extends GuiScreen {

    private BetterHUD BH;
    private BaseWindow BW;
    private Minecraft mc;

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
    private boolean border = false;
    private int cooldown = 0;
    private boolean isCooldown = false;

    public Colorizer(BetterHUD BH, BaseWindow BW, Minecraft mc) {
        this.BH = BH;
        this.BW = BW;
        this.mc = mc;
        getInts();
        this.thickness = (float) BW.getThickness();
        mc.displayGuiScreen(this);
    }

    public void update() {
        getInts();
        mouse();
        render();
    }

    private void getInts() {
        r = BW.getR();
        g = BW.getG();
        b = BW.getB();
        a = BW.getA();
        r1 = BW.getBorderR();
        g1 = BW.getBorderG();
        b1 = BW.getBorderB();
        a1 = BW.getBorderA();
        thickness = (float) BW.getThickness();
    }

    private void mouse() {
        if (isCooldown) {
            cooldown++;
        }
        if (cooldown % 2 == 0 && isCooldown) {
            isCooldown = false;
        }
        if (Mouse.isButtonDown(0)) {
            ScaledResolution var1 = new ScaledResolution(mc);
            x = Mouse.getEventX() * this.width / mc.displayWidth;
            y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
            dx = x - lastX;
            dy = y - lastY;
            lastX = x;
            lastY = y;
            if (!border) {
                r = r <= 0 ? 0 : r;
                r = r > 255 ? 255 : r;
                g = g <= 0 ? 0 : g;
                g = g > 255 ? 255 : g;
                b = b <= 0 ? 0 : b;
                b = b > 255 ? 255 : b;
                a = a < 0 ? 0 : a;
                a = a > 255 ? 255 : a;
                if ((y < ry + 7 && y >= ry && x < rx + 5 && x >= rx) || grabbedr) {
                    grabbedr = true;
                    BW.setRGBA(r + dx, g, b, a);
                } else if ((y < gy + 7 && y >= gy && x < gx + 5 && x >= gx) || grabbedg) {
                    grabbedg = true;
                    BW.setRGBA(r, g + dx, b, a);
                } else if ((y < by + 7 && y >= by && x < bx + 5 && x >= bx) || grabbedb) {
                    grabbedb = true;
                    BW.setRGBA(r, g, b + dx, a);
                } else if ((y < ay + 7 && y >= ay && x < ax + 5 && x >= ax) || grabbeda) {
                    grabbeda = true;
                    BW.setRGBA(r, g, b, a + dx);
                } else if (x <= var1.getScaledWidth() / 2 + 150 + 12 && x >= var1.getScaledWidth() / 2 + 150
                        && y <= var1.getScaledHeight() / 2 - 25 + 60 + 9 && y >= var1.getScaledHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= var1.getScaledWidth() / 2 + 150 + 12 + 21 && x >= var1.getScaledWidth() / 2 + 150
                        && y <= var1.getScaledHeight() / 2 - 25 + 70 + 9 && y >= var1.getScaledHeight() / 2 - 25 + 70) {
                    border = true;
                }
            } else {
                r1 = r1 <= 0 ? 0 : r1;
                r1 = r1 > 255 ? 255 : r1;
                g1 = g1 <= 0 ? 0 : g1;
                g1 = g1 > 255 ? 255 : g1;
                b1 = b1 <= 0 ? 0 : b1;
                b1 = b1 > 255 ? 255 : b1;
                a1 = a1 <= 0 ? 0 : a1;
                a1 = a1 > 255 ? 255 : a1;
                thickness = thickness <= 0 ? 0 : thickness;
                thickness = thickness > 255 ? 255 : thickness;
                if ((y < ry + 7 && y >= ry && x < rx + 5 && x >= rx) || grabbedr) {
                    grabbedr = true;
                    BW.setBorderRGB(r1 + dx, g1, b1, a1);
                } else if ((y < gy + 7 && y >= gy && x < gx + 5 && x >= gx) || grabbedg) {
                    grabbedg = true;
                    BW.setBorderRGB(r1, g1 + dx, b1, a1);
                } else if ((y < by + 7 && y >= by && x < bx + 5 && x >= bx) || grabbedb) {
                    grabbedb = true;
                    BW.setBorderRGB(r1, g1, b1 + dx, a1);
                } else if ((y < ay + 7 && y >= ay && x < ax + 5 && x >= ax) || grabbeda) {
                    grabbeda = true;
                    BW.setBorderRGB(r1, g1, b1, a1 + dx);
                } else if ((y < ty + 7 && y >= ty && x < tx + 5 && x >= tx) || grabbedthickness) {
                    BW.setThickness(thickness / 255.0f + (float) (dx / 255));
                    grabbedthickness = true;
                } else if (x <= var1.getScaledWidth() / 2 + 150 + 12 && x >= var1.getScaledWidth() / 2 + 150
                        && y <= var1.getScaledHeight() / 2 - 25 + 60 + 9 && y >= var1.getScaledHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= var1.getScaledWidth() / 2 + 150 + 12 + 21 && x >= var1.getScaledWidth() / 2 + 150
                        && y <= var1.getScaledHeight() / 2 - 25 + 70 + 9 && y >= var1.getScaledHeight() / 2 - 25 + 70) {
                    border = true;
                }
            }
            BW.setThickness(0.5f);
            BW.save();
        } else {
            cooldown = 0;
            grabbedr = false;
            grabbedg = false;
            grabbedb = false;
            grabbeda = false;
            grabbedthickness = false;
            lastX = Mouse.getEventX() * this.width / mc.displayWidth;
            lastY = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
        }
    }

    private void render() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);
        ScaledResolution var1 = new ScaledResolution(mc);
        if (!border) {
            rx = var1.getScaledWidth() / 2 - 127 + r;
            ry = var1.getScaledHeight() / 2 - 2 - 32;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 - 32, "Red");
            renderKnob(rx, ry, "red");
            gx = var1.getScaledWidth() / 2 - 127 + g;
            gy = var1.getScaledHeight() / 2 - 2;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2, "Green");
            renderKnob(gx, gy, "green");
            bx = var1.getScaledWidth() / 2 - 127 + b;
            by = var1.getScaledHeight() / 2 - 2 + 32;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 + 32, "Blue");
            renderKnob(bx, by, "blue");
            ax = var1.getScaledWidth() / 2 - 127 + a;
            ay = var1.getScaledHeight() / 2 - 2 + 64;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(ax, ay, "alpha");
        } else {
            rx = var1.getScaledWidth() / 2 - 127 + r1;
            ry = var1.getScaledHeight() / 2 - 2 - 32;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 - 32, "Red");
            renderKnob(rx, ry, "red");
            gx = var1.getScaledWidth() / 2 - 127 + g1;
            gy = var1.getScaledHeight() / 2 - 2;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2, "Green");
            renderKnob(gx, gy, "green");
            bx = var1.getScaledWidth() / 2 - 127 + b1;
            by = var1.getScaledHeight() / 2 - 2 + 32;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 + 32, "Blue");
            renderKnob(bx, by, "blue");
            ax = var1.getScaledWidth() / 2 - 127 + a1;
            ay = var1.getScaledHeight() / 2 - 2 + 64;
            renderSlider(var1.getScaledWidth() / 2 - 127, var1.getScaledHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(ax, ay, "alpha");
        }
        BH.drawHUDRectWithBorder(var1.getScaledWidth() / 2 + 150, var1.getScaledHeight() / 2 - 25, 50, 50,
                r, g, b, a, r1, g1, b1, a1, BW.getThickness());
        int px = var1.getScaledWidth() / 2 + 150 - 1;
        int py = var1.getScaledHeight() / 2 - 25 + 60;
        int pwidth = 13;
        int pheight = 9;
        if (!border) {
            BH.drawHUDRectWithBorder(px, py, pwidth, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            BH.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        } else {
            BH.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            BH.drawHUDRectWithBorder(px, py, pwidth, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        }
        BH.drawShadowedFont("BG", var1.getScaledWidth() / 2 + 150, var1.getScaledHeight() / 2 - 25 + 60, 0xFFFFFF);
        BH.drawShadowedFont("Outline", var1.getScaledWidth() / 2 + 150, var1.getScaledHeight() / 2 - 25 + 70, 0xFFFFFF);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    private void renderSlider(int x, int y, String effector) {
        if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0 || a > 255 || a < 0
                || r1 > 255 || r1 < 0 || g1 > 255 || g1 < 0 || b1 > 255 || b1 < 0 || a1 > 255 || a1 < 0) {
            BW.setRGBA(Math.abs(r <= 255 ? r : 255), Math.abs(g <= 255 ? g : 255),
                    Math.abs(b <= 255 ? b : 255), Math.abs(a <= 255 ? a : 255));
            BW.setBorderRGB(Math.abs(r1 <= 255 ? r1 : 255), Math.abs(g1 <= 255 ? g1 : 255),
                    Math.abs(b1 <= 255 ? b1 : 255), Math.abs(a1 <= 255 ? a1 : 255));
            getInts();
        }
        BH.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        GlStateManager.enableTexture2D();
        y -= 10;
        if (!border) {
            if (effector.equalsIgnoreCase("Red")) {
                BH.drawShadowedFont(effector + ": " + r, x, y, -1);
            } else if (effector.equalsIgnoreCase("Green")) {
                BH.drawShadowedFont(effector + ": " + g, x, y, -1);
            } else if (effector.equalsIgnoreCase("Blue")) {
                BH.drawShadowedFont(effector + ": " + b, x, y, -1);
            } else if (effector.equalsIgnoreCase("Alpha")) {
                BH.drawShadowedFont(effector + ": " + a, x, y, -1);
            }
        } else if (effector.equalsIgnoreCase("Red")) {
            BH.drawShadowedFont(effector + ": " + r1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Green")) {
            BH.drawShadowedFont(effector + ": " + g1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Blue")) {
            BH.drawShadowedFont(effector + ": " + b1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Alpha")) {
            BH.drawShadowedFont(effector + ": " + a1, x, y, -1);
        } else if (effector.equalsIgnoreCase("Thickness")) {
            BH.drawShadowedFont(effector + ": " + thickness / 255.0f, x, y, -1);
        }
    }

    private void renderKnob(int x, int y, String color) {
        if (!border) {
            if (color.equalsIgnoreCase("red")) {
                BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("green")) {
                BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("blue")) {
                BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("alpha")) {
                BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
            }
        } else if (color.equalsIgnoreCase("red")) {
            BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("green")) {
            BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("blue")) {
            BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("alpha")) {
            BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("thickness")) {
            BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 0, 100, 255, 255, 255, 255, 0.3f);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
