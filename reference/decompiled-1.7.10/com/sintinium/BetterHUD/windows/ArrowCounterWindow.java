/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  adb
 *  add
 *  bao
 *  org.lwjgl.opengl.GL11
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordsGUI;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;

public class ArrowCounterWindow
extends DefaultWindow {
    BetterHUD BH;
    private int x = 16;
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
    private int flash = 0;
    private float thickness = 0.8f;
    private ArrayList<Double> data = new ArrayList();
    private FileManager FM;
    private add[] inventroy = new add[36];
    private adb item;
    private int nextFlash = 2;
    private boolean shouldFlash = true;
    private int count = 0;
    private int timer = 0;
    private int secondTimer = 0;
    private long lastTime;
    private bao mc;
    private double toggle = 1.0;

    public ArrowCounterWindow(BetterHUD BH, bao mc) {
        this.mc = mc;
        this.BH = BH;
        this.FM = new FileManager("Arrow", 3);
        this.load();
    }

    @Override
    public String getToolTip() {
        return "Shows how many arrows you have. Click to change modes";
    }

    @Override
    public void setToDefault() {
        this.setX(16);
        this.setY(68);
        this.save();
    }

    @Override
    public void toggle() {
        this.toggle = this.toggle == 0.0 ? 1.0 : (this.toggle == 1.0 ? 2.0 : 0.0);
    }

    @Override
    public int getToggled() {
        if (this.toggle == 2.0) {
            return 1;
        }
        return (int)this.toggle;
    }

    @Override
    public void update() {
        this.count = 0;
        this.inventroy = this.mc.h.bm.a;
        for (int i = 0; i < this.inventroy.length; ++i) {
            if (this.inventroy[i] == null) continue;
            this.item = this.inventroy[i].b();
            if (adb.b((adb)this.item) != 262) continue;
            this.count += this.inventroy[i].b;
        }
        GL11.glEnable((int)3042);
        GL11.glDepthMask((boolean)false);
        GL11.glBlendFunc((int)770, (int)771);
        if (this.count <= 5 && this.shouldFlash) {
            if (this.timer < 3 && System.currentTimeMillis() - this.lastTime >= 500L) {
                if (System.currentTimeMillis() - this.lastTime >= 1000L) {
                    this.lastTime = System.currentTimeMillis();
                    ++this.timer;
                } else {
                    this.BH.drawHUDRectWithBorder(this.getX() + 2, this.getY() + 2, this.getWidth() + 1, this.getHeight() + 1, 255.0, 0.0, 0.0, 255.0, 0.0, 0.0, 0.0, 255.0, 1.0);
                }
            } else if (this.timer >= 3) {
                this.shouldFlash = false;
            }
        } else if (this.count > 5) {
            this.timer = 0;
            this.shouldFlash = true;
            this.nextFlash = 4;
            this.lastTime = System.currentTimeMillis();
        }
        this.BH.drawItemSprite(this.x, this.y, 262, this);
        if (this.toggle != 2.0 || this.count < 64) {
            this.mc.l.a(this.count + "", this.x + 11, this.y + 9, -1);
        } else if (this.count > 64 && this.count % 64 != 0) {
            this.mc.l.a("[" + (int)Math.floor(this.count / 64) + "]+" + this.count % 64, this.x + 11, this.y + 9, -1);
        } else if (this.count >= 64 && this.count % 64 == 0) {
            this.mc.l.a("[" + (int)Math.floor(this.count / 64) + "]", this.x + 11, this.y + 9, -1);
        }
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)3042);
        if ((int)this.toggle == 0) {
            this.mc.l.a("X", this.x, this.y, -1);
        }
        if ((int)this.toggle == 1 && CoordsGUI.guiOpen) {
            this.mc.l.a("Sum", this.x, this.y, -1);
        }
        if ((int)this.toggle == 2 && CoordsGUI.guiOpen) {
            this.mc.l.a("Stacks", this.x, this.y, -1);
        }
    }

    @Override
    public String getName() {
        return "ArrowCounter";
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
        return 13;
    }

    @Override
    public int getHeight() {
        return 13;
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
            this.toggle = this.data.get(2).intValue();
        }
    }
}
