/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  bca
 *  bdw
 *  org.lwjgl.input.Keyboard
 *  org.lwjgl.input.Mouse
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordsGUI;
import com.sintinium.BetterHUD.windows.FileManager;
import java.util.ArrayList;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class OptionMenu
extends bdw {
    private bao mc;
    private CoordsGUI cGui;
    private BetterHUD BH;
    private FileManager FM;
    private ArrayList<Double> data = new ArrayList();
    private double drag;
    private double bright;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int width1;
    private int width2;
    private int height1;
    private int height2;
    private String text1;
    private String text2;
    private double button1;
    private double button2;
    private boolean set1;
    private boolean set2;
    String func1 = "Draggable HUD";
    String func2 = "Full Gamma Bright";

    public OptionMenu(bao mc, CoordsGUI cGui, BetterHUD BH) {
        this.mc = mc;
        this.cGui = cGui;
        this.BH = BH;
        this.width1 = 40;
        this.width2 = 40;
        this.height1 = 10;
        this.height2 = 10;
        this.FM = new FileManager("keys.txt", 2);
        this.data = this.FM.getArray();
        this.drag = (int)this.data.get(0).doubleValue();
        this.bright = (int)this.data.get(1).doubleValue();
        this.text1 = Keyboard.getKeyName((int)((int)this.drag));
        this.text2 = Keyboard.getKeyName((int)((int)this.bright));
    }

    public void render() {
        if (Mouse.isButtonDown((int)0) && !this.set1 && !this.set2) {
            this.mouse();
        } else if (this.set1) {
            if (Keyboard.getEventKeyState()) {
                this.button1 = Keyboard.getEventKey();
                this.text1 = Keyboard.getKeyName((int)((int)this.button1));
                this.set1 = false;
                this.data.set(0, this.button1);
                this.save();
            }
        } else if (this.set2 && Keyboard.getEventKeyState()) {
            this.button2 = Keyboard.getEventKey();
            this.text2 = Keyboard.getKeyName((int)((int)this.button2));
            this.set2 = false;
            this.data.set(1, this.button2);
            this.save();
        }
        bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
        this.x1 = var1.a() / 2 - this.width1 / 2;
        this.y1 = var1.b() / 2 - this.height1 / 2 - 22;
        this.x2 = var1.a() / 2 - this.width2 / 2;
        this.y2 = var1.b() / 2 - this.height2 / 2 + 22;
        this.BH.drawHUDRectWithBorder(this.x1, this.y1, this.width1, this.height1, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 1.5);
        this.mc.l.a(this.func1, this.x1 - this.mc.l.a(this.func1) / 4, this.y1 - 12, -1);
        this.BH.drawHUDRectWithBorder(this.x2, this.y2, this.width2, this.height2, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 1.5);
        this.mc.l.a(this.func2, this.x2 - this.mc.l.a(this.func2) / 4, this.y2 - 12, -1);
        if (!this.set1 && !this.set2) {
            this.mc.l.a(this.text1, this.x1 + 1, this.y1 + 1, -1);
            this.mc.l.a(this.text2, this.x2 + 1, this.y2 + 1, -1);
        } else if (this.set1) {
            this.mc.l.a(this.text1, this.x1 + 1, this.y1 + 1, -65536);
            this.mc.l.a(this.text2, this.x2 + 1, this.y2 + 1, -1);
        } else if (this.set2) {
            this.mc.l.a(this.text1, this.x1 + 1, this.y1 + 1, -1);
            this.mc.l.a(this.text2, this.x2 + 1, this.y2 + 1, -65536);
        }
    }

    public void reset() {
        this.set1 = false;
        this.set2 = false;
    }

    public void save() {
        this.FM.setArray(this.data);
    }

    public void mouse() {
        bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
        int x = Mouse.getEventX() * this.l / this.mc.d;
        int y = this.m - Mouse.getEventY() * this.m / this.mc.e - 1;
        if (x >= this.x1 && x <= this.x1 + this.width1 && y >= this.y1 && y <= this.y1 + this.height1) {
            this.set1 = true;
        }
        if (x >= this.x2 && x <= this.x2 + this.width2 && y >= this.y2 && y <= this.y2 + this.height2) {
            this.set2 = true;
        }
    }
}
