/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adb
 *  add
 *  bam
 *  bao
 *  bbu
 *  bca
 *  bdw
 *  bny
 *  fj
 *  fr
 *  org.lwjgl.opengl.GL11
 */
package com.sintinium.BetterHUD;

import com.sintinium.BetterHUD.VersionChecker;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.WindowManager;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class BetterHUD
extends bdw {
    public boolean shouldRender = false;
    public boolean move = false;
    private bbu FR;
    private WindowManager WM;
    private bny RI;
    private bao mc;
    private String currentVersion = "1.2";
    private String update;
    private ArrayList updateCheck = new ArrayList();
    private static boolean checkedUpdate = false;
    private VersionChecker vc;
    private boolean newVersion = false;
    private int timer;
    public static boolean resetDefaults = false;

    public BetterHUD(bbu FR, bao mc) {
        this.FR = FR;
        this.WM = new WindowManager(this, FR, mc);
        this.RI = new bny();
        this.mc = mc;
        this.vc = new VersionChecker();
        this.updateCheck = this.vc.getCurrentVersion();
        try {
            this.update = (String)this.updateCheck.get(0);
            if (!this.update.equals(this.currentVersion) && !checkedUpdate) {
                this.newVersion = true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void setRenderState(boolean renderState) {
        this.shouldRender = renderState;
    }

    public void update() {
        if (this.shouldRender) {
            this.render();
            this.WM.update();
        }
        if (this.newVersion) {
            if (!checkedUpdate) {
                checkedUpdate = true;
            }
            this.mc.h.b((fj)new fr("UHC Essentials version " + this.update + " is ready for download", new Object[0]));
            for (int i = 1; i < this.updateCheck.size(); ++i) {
                this.mc.h.b((fj)new fr((String)this.updateCheck.get(i), new Object[0]));
            }
            this.newVersion = false;
        }
    }

    public void render() {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        this.WM.render();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public void drawItemSprite(int xPos, int yPos, int itemID, DefaultWindow DW) {
        GL11.glEnable((int)32826);
        GL11.glPushMatrix();
        bam.c();
        bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
        this.RI.a(this.q, this.mc.P(), new add(adb.d((int)itemID)), xPos, yPos);
        bam.a();
        GL11.glDisable((int)32826);
        GL11.glEnable((int)32826);
        GL11.glPopMatrix();
    }

    public void drawHUDRect(double x1, double y1, double width, double height, double red, double green, double blue, double alpha) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        red = Double.valueOf(red > 255.0 ? 255.0 : red) / 255.0;
        green = Double.valueOf(green > 255.0 ? 255.0 : green) / 255.0;
        blue = Double.valueOf(blue > 255.0 ? 255.0 : blue) / 255.0;
        alpha = Double.valueOf(alpha > 255.0 ? 255.0 : alpha) / 255.0;
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)1, (int)0);
        GL11.glColor4d((double)red, (double)green, (double)blue, (double)alpha);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)(y1 + height));
        GL11.glVertex2d((double)(x1 + width), (double)(y1 + height));
        GL11.glVertex2d((double)(x1 + width), (double)y1);
        GL11.glEnd();
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public void drawShadowedFont(String text, int x, int y, int color) {
        this.mc.l.a(text, x, y, color);
    }

    public bbu getFontRenderer() {
        return this.mc.l;
    }

    public void drawHUDRectWithBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double red2, double green2, double blue2, double alpha2, double thickness, boolean left, boolean right, boolean top, boolean bottom) {
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        red = Double.valueOf(red > 255.0 ? 255.0 : red) / 255.0;
        green = Double.valueOf(green > 255.0 ? 255.0 : green) / 255.0;
        blue = Double.valueOf(blue > 255.0 ? 255.0 : blue) / 255.0;
        alpha = Double.valueOf(alpha > 255.0 ? 255.0 : alpha) / 255.0;
        red2 = Double.valueOf(red2 > 255.0 ? 255.0 : red2) / 255.0;
        green2 = Double.valueOf(green2 > 255.0 ? 255.0 : green2) / 255.0;
        blue2 = Double.valueOf(blue2 > 255.0 ? 255.0 : blue2) / 255.0;
        alpha2 = Double.valueOf(alpha2 > 255.0 ? 255.0 : alpha2) / 255.0;
        GL11.glColor4d((double)red, (double)green, (double)blue, (double)alpha);
        GL11.glBegin((int)7);
        GL11.glVertex2d((double)x1, (double)y1);
        GL11.glVertex2d((double)x1, (double)(y1 + height));
        GL11.glVertex2d((double)(x1 + width), (double)(y1 + height));
        GL11.glVertex2d((double)(x1 + width), (double)y1);
        GL11.glEnd();
        GL11.glColor4d((double)red2, (double)green2, (double)blue2, (double)alpha2);
        if (left || right || bottom || top) {
            if (left && !bottom) {
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)(x1 - thickness), (double)(y1 - thickness));
                GL11.glVertex2d((double)(x1 - thickness), (double)(y1 + height));
                GL11.glVertex2d((double)x1, (double)(y1 + height));
                GL11.glVertex2d((double)x1, (double)(y1 - thickness));
                GL11.glEnd();
            } else if (left) {
                GL11.glColor4d((double)red2, (double)green2, (double)blue2, (double)alpha2);
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)(x1 - thickness), (double)(y1 - thickness));
                GL11.glVertex2d((double)(x1 - thickness), (double)(y1 + height + thickness));
                GL11.glVertex2d((double)x1, (double)(y1 + height + thickness));
                GL11.glVertex2d((double)x1, (double)(y1 - thickness));
                GL11.glEnd();
            }
            if (right && !bottom) {
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)(x1 + width), (double)(y1 - thickness));
                GL11.glVertex2d((double)(x1 + width), (double)(y1 + height));
                GL11.glVertex2d((double)(x1 + width + thickness), (double)(y1 + height));
                GL11.glVertex2d((double)(x1 + width + thickness), (double)(y1 - thickness));
                GL11.glEnd();
            } else if (right) {
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)(x1 + width), (double)(y1 - thickness));
                GL11.glVertex2d((double)(x1 + width), (double)(y1 + height + thickness));
                GL11.glVertex2d((double)(x1 + width + thickness), (double)(y1 + height + thickness));
                GL11.glVertex2d((double)(x1 + width + thickness), (double)(y1 - thickness));
                GL11.glEnd();
            }
            if (!bottom) {
                GL11.glColor4d((double)red2, (double)green2, (double)blue2, (double)(alpha2 - (double)0.2f));
            }
            if (bottom) {
                GL11.glColor4d((double)red2, (double)green2, (double)blue2, (double)alpha2);
            }
            if (top) {
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)x1, (double)(y1 - thickness));
                GL11.glVertex2d((double)x1, (double)y1);
                GL11.glVertex2d((double)(x1 + width), (double)y1);
                GL11.glVertex2d((double)(x1 + width), (double)(y1 - thickness));
                GL11.glEnd();
            }
            if (bottom) {
                GL11.glBegin((int)7);
                GL11.glVertex2d((double)x1, (double)(y1 + height));
                GL11.glVertex2d((double)x1, (double)(y1 + height + thickness));
                GL11.glVertex2d((double)(x1 + width), (double)(y1 + height + thickness));
                GL11.glVertex2d((double)(x1 + width), (double)(y1 + height));
                GL11.glEnd();
            }
        }
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
    }

    public void drawHUDRectWithBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double red2, double green2, double blue2, double alpha2, double thickness) {
        this.drawHUDRectWithBorder(x1, y1, width, height, red, green, blue, alpha, red2, green2, blue2, alpha2, thickness, true, true, true, true);
    }

    public void drawHUDRectBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double thickness) {
        this.drawHUDRectWithBorder(x1, y1, width, height, 0.0, 0.0, 0.0, 0.0, red, green, blue, alpha, thickness);
    }
}
