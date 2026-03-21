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
import com.sintinium.BetterHUD.windows.Colorizer;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import com.sintinium.BetterHUD.windows.OptionMenu;
import com.sintinium.BetterHUD.windows.TipWindow;
import com.sintinium.BetterHUD.windows.WindowManager;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class CoordsGUI
extends bdw {
    private static boolean[] keyStates;
    private WindowManager WM;
    private BetterHUD BH;
    private bao mc;
    private OptionMenu om;
    private boolean fullbright = false;
    private boolean shouldChange = false;
    private double gamma;
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private int dx = 0;
    private int dy = 0;
    private boolean mouseFree = false;
    private boolean grabbed = false;
    private DefaultWindow DW;
    private boolean moved = false;
    private int drag = 54;
    private int bright = 48;
    private int lastMove;
    private boolean firstDrag = false;
    private boolean on = false;
    private Colorizer color;
    private FileManager FM;
    private FileManager FM1;
    private ArrayList<Double> data;
    private ArrayList<Double> data1;
    private boolean pressed = false;
    private int previewAlpha = 255;
    private int previewR = 255;
    private int previewG = 255;
    private int previewB = 255;
    private boolean alphaDown = true;
    private Random random = new Random();
    public long lastTime;
    public static boolean guiOpen;
    private boolean optionsMenu = false;
    public CoordinateWindow cWindow;
    private String resetAll = "Reset All Windows";
    private String toggleButton = "Toggle UHC Essentials";
    private String copyCoordinates = "Copy coordinates to clipboard(or press NumPad7)";
    String options = "Options";

    public CoordsGUI(WindowManager WM, bao mc, BetterHUD BH) {
        this.WM = WM;
        keyStates = new boolean[256];
        this.mc = mc;
        this.BH = BH;
        this.cWindow = WM.CW;
        this.FM = new FileManager("Gamma", 1);
        this.FM1 = new FileManager("keys.txt", 2);
        this.data1 = this.FM1.getArray();
        if (this.data1.size() < 2) {
            this.data1.add(Double.valueOf(this.drag));
            this.data1.add(Double.valueOf(this.bright));
            this.FM1.setArray(this.data1);
            this.data1.clear();
        } else {
            this.drag = (int)this.data1.get(0).doubleValue();
            this.bright = (int)this.data1.get(1).doubleValue();
        }
        this.om = new OptionMenu(this.mc, this, this.BH);
        this.data = this.FM.getArray();
        if (this.data.size() < 1) {
            this.data.add(Double.valueOf(this.mc.u.aG));
            this.FM.setArray(this.data);
            this.data.clear();
        } else {
            this.gamma = this.data.get(0);
            this.mc.u.aG = (float)this.gamma;
        }
    }

    public void save() {
        this.data.clear();
        this.data.add(this.gamma);
        this.FM.setArray(this.data);
    }

    public void update() {
        this.checkKeys();
        if (this.color != null && this.on) {
            this.color.update();
        }
        if (this.mouseFree && !this.optionsMenu && !this.on) {
            bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
            String reset = "Options";
            this.BH.drawHUDRectWithBorder(var1.a() / 2 - this.BH.getFontRenderer().a(reset) / 2, var1.b() / 2 - 5, this.BH.getFontRenderer().a(reset) + 1, 10.0, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 0.5);
            this.BH.drawShadowedFont(reset, var1.a() / 2 - this.BH.getFontRenderer().a(reset) / 2 + 1, var1.b() / 2 - 4, -1);
            this.BH.drawHUDRectWithBorder(var1.a() / 2 - this.BH.getFontRenderer().a(this.resetAll) / 2, var1.b() / 2 - 5 + 12, this.BH.getFontRenderer().a(this.resetAll) + 1, 10.0, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 0.5);
            this.BH.drawShadowedFont(this.resetAll, var1.a() / 2 - this.BH.getFontRenderer().a(this.resetAll) / 2 + 1, var1.b() / 2 - 4 + 12, -1);
            this.BH.drawHUDRectWithBorder(var1.a() / 2 - this.BH.getFontRenderer().a(this.toggleButton) / 2, var1.b() / 2 - 5 + 24, this.BH.getFontRenderer().a(this.toggleButton) + 1, 10.0, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 0.5);
            this.BH.drawShadowedFont(this.toggleButton, var1.a() / 2 - this.BH.getFontRenderer().a(this.toggleButton) / 2 + 1, var1.b() / 2 - 4 + 24, -1);
            this.BH.drawHUDRectWithBorder(var1.a() / 2 - this.BH.getFontRenderer().a(this.copyCoordinates) / 2, var1.b() / 2 - 5 + 36, this.BH.getFontRenderer().a(this.copyCoordinates) + 1, 10.0, 0.0, 0.0, 0.0, 255.0, 255.0, 255.0, 255.0, 255.0, 0.5);
            this.BH.drawShadowedFont(this.copyCoordinates, var1.a() / 2 - this.BH.getFontRenderer().a(this.copyCoordinates) / 2 + 1, var1.b() / 2 - 4 + 36, -1);
        }
        if (this.mouseFree && !this.optionsMenu) {
            this.WM.armorWindow.updateInGUI();
            this.drag();
            if (this.WM.tipWindow.closeTip && TipWindow.gotTips) {
                this.WM.tipWindow.newTip();
                this.WM.tipWindow.closeTip = false;
            }
        } else {
            this.WM.tipWindow.closeTip = true;
        }
        if (this.optionsMenu) {
            if (this.mc.n == null) {
                this.mc.a((bdw)this.om);
            }
            this.om.render();
        }
        if (this.fullbright) {
            this.mc.u.aG = 2000.0f;
        } else if (this.shouldChange) {
            this.mc.u.aG = (float)this.gamma;
            this.shouldChange = false;
            this.save();
        } else if (this.gamma != (double)this.mc.u.aG) {
            this.gamma = this.mc.u.aG;
            this.save();
        }
    }

    public static boolean checkKey(int i) {
        if (Keyboard.isKeyDown((int)i) != keyStates[i]) {
            CoordsGUI.keyStates[i] = !keyStates[i];
            return CoordsGUI.keyStates[i];
        }
        return false;
    }

    public void checkKeys() {
        if (Keyboard.getEventKeyState()) {
            this.FM1 = new FileManager("keys.txt", 2);
            this.data1 = this.FM1.getArray();
            if (this.data1.size() < 2) {
                this.data1.add(Double.valueOf(this.drag));
                this.data1.add(Double.valueOf(this.bright));
                this.FM1.setArray(this.data1);
                this.data1.clear();
            } else {
                this.drag = (int)this.data1.get(0).doubleValue();
                this.bright = (int)this.data1.get(1).doubleValue();
            }
        }
        if (this.mc.n == null && !this.optionsMenu) {
            if (CoordsGUI.checkKey(this.drag)) {
                guiOpen = true;
                this.WM.showAll();
                this.mouseFree = true;
                this.mc.a((bdw)this);
            } else if (CoordsGUI.checkKey(this.bright)) {
                this.fullbright = !this.fullbright;
                this.shouldChange = true;
            } else if (CoordsGUI.checkKey(71)) {
                String myString = "x: " + (int)this.mc.h.s + " y: " + (int)(this.mc.h.t - 1.0) + " z: " + (int)this.mc.h.u;
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        }
        if (CoordsGUI.checkKey(1)) {
            guiOpen = false;
            this.optionsMenu = false;
            this.mouseFree = false;
            this.om.reset();
            this.WM.reset();
            this.on = false;
            this.lastX = 0;
            this.lastY = 0;
            this.dx = 0;
            this.dy = 0;
            this.grabbed = false;
            this.color = null;
        }
    }

    public void drag() {
        int i;
        this.x = Mouse.getX();
        this.y = Mouse.getY();
        this.x = Mouse.getEventX() * this.l / this.mc.d;
        this.y = this.m - Mouse.getEventY() * this.m / this.mc.e - 1;
        this.dx = this.x - this.lastX;
        this.dy = this.y - this.lastY;
        this.lastX = this.x;
        this.lastY = this.y;
        bca var1 = new bca(this.mc, this.mc.d, this.mc.e);
        int Twidth = var1.a();
        int Theight = var1.b();
        if (Mouse.isButtonDown((int)0) && (!this.pressed || this.grabbed) && !this.optionsMenu) {
            if (!Mouse.isButtonDown((int)3)) {
                if (this.x >= Twidth / 2 - this.BH.getFontRenderer().a(this.options) / 2 && this.x <= Twidth / 2 + this.BH.getFontRenderer().a(this.options) / 2 && this.y >= Theight / 2 - 22 && this.y <= Theight / 2 + 22) {
                    this.WM.reset();
                }
                if (!this.firstDrag) {
                    this.lastMove = 0;
                }
                if (!this.grabbed) {
                    for (i = 0; i < this.WM.DW.size(); ++i) {
                        if (this.x >= this.WM.DW.get(i).getX() && this.x <= this.WM.DW.get(i).getX() + this.WM.DW.get(i).getWidth()) {
                            if (this.y < this.WM.DW.get(i).getY() || this.y > this.WM.DW.get(i).getY() + this.WM.DW.get(i).getHeight()) continue;
                            this.DW = this.WM.DW.get(i);
                            this.grabbed = true;
                            this.firstDrag = true;
                            return;
                        }
                        if (this.x >= Twidth / 2 - this.BH.getFontRenderer().a(this.options) / 2 && this.x <= Twidth / 2 + this.BH.getFontRenderer().a(this.options) / 2 && this.y >= Theight / 2 - 5 && this.y <= Theight / 2 + 5 && !this.on) {
                            this.mc.a(null);
                            this.optionsMenu = true;
                            return;
                        }
                        if (this.x >= Twidth / 2 - this.BH.getFontRenderer().a(this.resetAll) / 2 && this.x <= Twidth / 2 + this.BH.getFontRenderer().a(this.resetAll) / 2 && this.y >= Theight / 2 - 5 + 12 && this.y <= Theight / 2 + 5 + 12 && !this.on) {
                            this.WM.resetAllWindowsPositions();
                            this.pressed = true;
                            return;
                        }
                        if (this.x >= Twidth / 2 - this.BH.getFontRenderer().a(this.toggleButton) / 2 && this.x <= Twidth / 2 + this.BH.getFontRenderer().a(this.toggleButton) / 2 && this.y >= Theight / 2 - 5 + 24 && this.y <= Theight / 2 + 5 + 24 && !this.on) {
                            WindowManager.toggled = !WindowManager.toggled;
                            this.pressed = true;
                            return;
                        }
                        if (this.x < Twidth / 2 - this.BH.getFontRenderer().a(this.copyCoordinates) / 2 || this.x > Twidth / 2 + this.BH.getFontRenderer().a(this.copyCoordinates) / 2 || this.y < Theight / 2 - 5 + 36 || this.y > Theight / 2 + 5 + 36 || this.on) continue;
                        String myString = "x: " + (int)this.mc.h.s + " y: " + (int)(this.mc.h.t - 1.0) + " z: " + (int)this.mc.h.u;
                        StringSelection stringSelection = new StringSelection(myString);
                        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clpbrd.setContents(stringSelection, null);
                        this.pressed = true;
                        return;
                    }
                } else {
                    this.lastMove += this.dx + this.dy;
                    this.DW.setX(this.DW.getX() + this.dx);
                    this.DW.setY(this.DW.getY() + this.dy);
                    this.DW.save();
                }
                this.pressed = true;
            }
        } else if (this.lastMove == 0 && this.firstDrag) {
            this.firstDrag = false;
            this.DW.toggle();
            this.DW.save();
        } else {
            this.firstDrag = false;
            this.lastX = this.x;
            this.lastY = this.y;
            this.grabbed = false;
        }
        if (!Mouse.isButtonDown((int)0) && !Mouse.isButtonDown((int)1)) {
            this.pressed = false;
        }
        if (Mouse.isButtonDown((int)1) && !this.pressed) {
            this.pressed = true;
            for (i = 0; i < this.WM.DW.size(); ++i) {
                this.DW = this.WM.DW.get(i);
                if (this.x < this.WM.DW.get(i).getX() || this.x > this.WM.DW.get(i).getX() + this.WM.DW.get(i).getWidth() || this.y < this.WM.DW.get(i).getY() || this.y > this.WM.DW.get(i).getY() + this.WM.DW.get(i).getHeight() || !this.DW.getName().equalsIgnoreCase("Coordinate")) continue;
                if (!this.on) {
                    this.color = new Colorizer(this.BH, this.DW, this.mc);
                    this.on = true;
                    break;
                }
                this.color = null;
                this.on = false;
            }
        }
        if (!(Mouse.isButtonDown((int)0) || Mouse.isButtonDown((int)1) || this.pressed && !this.grabbed || this.optionsMenu)) {
            for (i = 0; i < this.WM.DW.size(); ++i) {
                if (this.x < this.WM.DW.get(i).getX() || this.x > this.WM.DW.get(i).getX() + this.WM.DW.get(i).getWidth() || this.y < this.WM.DW.get(i).getY() || this.y > this.WM.DW.get(i).getY() + this.WM.DW.get(i).getHeight()) continue;
                DefaultWindow DW = this.WM.DW.get(i);
                if (this.previewAlpha > 255) {
                    this.alphaDown = true;
                } else if (this.previewAlpha < 0 && DW.getName() == "Coordinate") {
                    this.alphaDown = false;
                    this.previewR = this.random.nextInt(255);
                    this.previewG = this.random.nextInt(255);
                    this.previewB = this.random.nextInt(255);
                } else if (this.previewAlpha < 150 && DW.getName() != "Coordinate") {
                    this.alphaDown = false;
                }
                int time = 10;
                if (DW.getName() == "Coordinate") {
                    time = 5;
                }
                if (System.currentTimeMillis() - this.lastTime > (long)time) {
                    this.previewAlpha = this.alphaDown ? (this.previewAlpha -= 2) : (this.previewAlpha += 2);
                    this.lastTime = System.currentTimeMillis();
                }
                if (DW.getToolTip().contains("`")) {
                    String[] split = DW.getToolTip().split("`");
                    int longestWidth = 0;
                    for (int splitLength = 0; splitLength < split.length; ++splitLength) {
                        if (this.BH.getFontRenderer().a(split[splitLength]) <= longestWidth) continue;
                        longestWidth = this.BH.getFontRenderer().a(split[splitLength]);
                    }
                    if (DW.getName() == "Coordinate") {
                        this.BH.drawHUDRectWithBorder(this.x - 1 + 10, this.y - 1, longestWidth + 2, split.length * 10, this.previewR, this.previewG, this.previewB, this.previewAlpha, this.cWindow.getBorderR(), this.cWindow.getBorderG(), this.cWindow.getBorderB(), this.previewAlpha, this.cWindow.getThickness());
                    } else {
                        this.BH.drawHUDRectWithBorder(this.x - 1 + 10, this.y - 1, longestWidth + 2, split.length * 10, this.cWindow.getR(), this.cWindow.getG(), this.cWindow.getB(), this.previewAlpha, this.cWindow.getBorderR(), this.cWindow.getBorderG(), this.cWindow.getBorderB(), this.cWindow.getBorderA(), this.cWindow.getThickness());
                    }
                    for (int j = 0; j < split.length; ++j) {
                        int width = this.BH.getFontRenderer().a(split[j]);
                        this.BH.drawShadowedFont(split[j], this.x + 10, this.y + j * 10, -1);
                    }
                    continue;
                }
                int width = this.BH.getFontRenderer().a(DW.getToolTip());
                this.BH.drawHUDRectWithBorder(this.x - 1 + 10, this.y - 1, width + 2, 10.0, this.cWindow.getR(), this.cWindow.getG(), this.cWindow.getB(), this.previewAlpha, this.cWindow.getBorderR(), this.cWindow.getBorderG(), this.cWindow.getBorderB(), this.cWindow.getBorderA(), this.cWindow.getThickness());
                this.BH.drawShadowedFont(DW.getToolTip(), this.x + 10, this.y, -1);
            }
            return;
        }
    }

    public void mouseClicked() {
    }

    public boolean d() {
        return false;
    }

    static {
        guiOpen = false;
    }
}
