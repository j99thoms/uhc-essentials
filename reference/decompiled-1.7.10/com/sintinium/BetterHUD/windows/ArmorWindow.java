/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adb
 *  bao
 *  org.lwjgl.opengl.GL11
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class ArmorWindow
extends DefaultWindow {
    BetterHUD BH;
    private int x = 2;
    private int y = 102;
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
    private ArrayList<Integer> armor = new ArrayList();
    private ArrayList<Float> armorDamage = new ArrayList();
    public static double toggle = 1.0;
    private float thickness = 0.8f;
    private ArrayList<Double> data = new ArrayList();
    private FileManager FM;
    private bao mc;
    private CoordinateWindow CW;

    public ArmorWindow(BetterHUD BH, bao mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.CW = CW;
        this.FM = new FileManager("Armor", 3);
        this.load();
    }

    @Override
    public void toggle() {
        toggle = toggle == 0.0 ? 1.0 : 0.0;
    }

    @Override
    public String getToolTip() {
        return "Shows how damaged your armor is";
    }

    @Override
    public void setToDefault() {
        this.setX(2);
        this.setY(102);
        this.save();
    }

    @Override
    public int getToggled() {
        return (int)toggle;
    }

    @Override
    public void update() {
        int i;
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        this.width = 14;
        this.height = 0;
        this.armor.clear();
        this.armorDamage.clear();
        for (i = 0; i < this.mc.h.bm.b.length; ++i) {
            if (this.mc.h.bm.b[this.mc.h.bm.b.length - i - 1] == null) continue;
            adb currItem = this.mc.h.bm.b[this.mc.h.bm.b.length - i - 1].b();
            int damage = this.mc.h.bm.b[this.mc.h.bm.b.length - i - 1].k();
            this.armor.add(adb.b((adb)currItem));
            float pDamage = (float)((double)damage / (double)currItem.o());
            this.armorDamage.add(Float.valueOf(pDamage));
        }
        for (i = 0; i < this.armor.size(); ++i) {
            int id = this.armor.get(i);
            float damage = this.armorDamage.get(i).floatValue();
            damage = (int)Math.round(100.0 - (double)damage * 100.0);
            int space = 15;
            int cx = this.x;
            int cy = this.y + i * space;
            this.BH.drawItemSprite(cx, cy, id, this);
            if (damage == 100.0f) {
                cx -= 2;
            } else if (damage < 10.0f) {
                cx += 3;
            }
            this.BH.drawShadowedFont((int)damage + "%", cx, cy + 10, 0xFFFFFF);
        }
        if ((int)toggle == 0 && this.armor.size() > 0) {
            this.BH.drawShadowedFont("X", this.x - 2, this.y - 2, 0xFFFFFF);
        }
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
    }

    public void updateInGUI() {
        if (this.armor.size() < 1) {
            GL11.glEnable((int)3042);
            GL11.glDepthMask((boolean)false);
            GL11.glBlendFunc((int)770, (int)771);
            this.width = this.BH.getFontRenderer().a("No Armor");
            this.height = 10;
            this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, this.width + 2, this.getHeight() + 2, this.CW.getR(), this.CW.getG(), this.CW.getB(), this.CW.getA(), this.CW.getBorderR(), this.CW.getBorderG(), this.CW.getBorderB(), this.CW.getBorderA(), this.CW.getThickness());
            this.BH.drawShadowedFont("No Armor", this.x, this.y, 0xFFFFFF);
            if ((int)toggle == 0) {
                this.BH.drawShadowedFont("X", this.x - 2, this.y - 2, 0xFFFFFF);
            }
            GL11.glDepthMask((boolean)true);
            GL11.glDisable((int)3042);
        }
    }

    @Override
    public void render() {
    }

    @Override
    public String getName() {
        return "Armor";
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
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.armor.size() * 14 + this.height;
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
