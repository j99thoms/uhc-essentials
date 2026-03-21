/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  org.lwjgl.opengl.GL11
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import org.lwjgl.opengl.GL11;

public class TipWindow
extends DefaultWindow {
    BetterHUD BH;
    private int x = 2;
    private int y = 220;
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
    public static double toggle = 0.0;
    private float thickness = 0.8f;
    private ArrayList<Double> data = new ArrayList();
    private FileManager FM;
    private bao mc;
    private CoordinateWindow CW;
    private ArrayList<String> tips;
    public static boolean gotTips = false;
    public Random random = new Random();
    public boolean closeTip = true;
    public int currTip = 0;
    private String tipOfThePage = "";

    public TipWindow(BetterHUD BH, bao mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.CW = CW;
        this.tips = new ArrayList();
    }

    @Override
    public void toggle() {
        if (toggle == 0.0 && !gotTips) {
            try {
                String inputLine;
                URL oracle = new URL("http://pastebin.com/raw.php?i=EErKqZMx");
                BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
                this.tips.clear();
                while ((inputLine = in.readLine()) != null) {
                    this.tips.add(inputLine);
                }
                in.close();
            }
            catch (Exception e) {
                System.out.println("[UHC ESSENTIALS] Couldn't fetch the tip :(.");
            }
            gotTips = true;
        } else {
            this.newTip();
        }
    }

    @Override
    public String getToolTip() {
        return "Note to user: Don't touch the tip`if you click again I'll show another tip ;)";
    }

    @Override
    public void setToDefault() {
        this.setX(2);
        this.setY(220);
    }

    @Override
    public int getToggled() {
        return (int)toggle;
    }

    @Override
    public void update() {
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        if (!gotTips) {
            String tipLoad = "Click me to load the tips!";
            this.width = this.BH.getFontRenderer().a(tipLoad);
            this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, this.width + 2, this.getHeight() + 2, this.CW.getR(), this.CW.getG(), this.CW.getB(), this.CW.getA(), this.CW.getBorderR(), this.CW.getBorderG(), this.CW.getBorderB(), this.CW.getBorderA(), this.CW.getThickness());
            this.BH.drawShadowedFont(tipLoad, this.x, this.y, -1);
        } else if (!this.tipOfThePage.contains("`")) {
            this.width = this.BH.getFontRenderer().a(this.tipOfThePage);
            this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, this.width + 2, this.getHeight() + 2, this.CW.getR(), this.CW.getG(), this.CW.getB(), this.CW.getA(), this.CW.getBorderR(), this.CW.getBorderG(), this.CW.getBorderB(), this.CW.getBorderA(), this.CW.getThickness());
            this.BH.drawShadowedFont(this.tipOfThePage, this.x, this.y, -1);
        } else {
            String[] split = this.tipOfThePage.split("`");
            int longestWidth = 0;
            for (int splitLength = 0; splitLength < split.length; ++splitLength) {
                if (this.BH.getFontRenderer().a(split[splitLength]) <= longestWidth) continue;
                longestWidth = this.BH.getFontRenderer().a(split[splitLength]);
            }
            this.width = longestWidth;
            this.BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, longestWidth + 2, split.length * 10, this.CW.getR(), this.CW.getG(), this.CW.getB(), this.CW.getA(), this.CW.getBorderR(), this.CW.getBorderG(), this.CW.getBorderB(), this.CW.getBorderA(), this.CW.getThickness());
            for (int j = 0; j < split.length; ++j) {
                this.BH.drawShadowedFont(split[j], this.x, this.y + j * 10, -1);
            }
        }
        GL11.glDisable((int)3042);
    }

    public void newTip() {
        this.tipOfThePage = this.tips.get(this.currTip);
        ++this.currTip;
        if (this.currTip >= this.tips.size()) {
            this.currTip = 0;
        }
    }

    @Override
    public void render() {
    }

    @Override
    public String getName() {
        return "Tips";
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
        return 8;
    }

    @Override
    public void save() {
    }

    @Override
    public void load() {
    }
}
