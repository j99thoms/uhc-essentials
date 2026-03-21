package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import com.j99thoms.uhcessentials.BetterHUD;

public class CoordinateWindow extends BaseWindow {

    private BetterHUD BH;
    private FontRenderer FR;
    private Minecraft mc;

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

    private double toggle = 1;

    private int width;
    private int height = 30;
    private double thickness = .8f;
    float scale = .75F;

    private FileManager FM;
    private ArrayList<Double> data = new ArrayList<Double>();

    public CoordinateWindow(BetterHUD BH, FontRenderer FR, Minecraft mc) {
        this.mc = mc;
        this.BH = BH;
        this.FR = FR;
        FM = new FileManager("Coord", 12);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows your position right click to change the color of everything!`And right click again to close out of the colorizer";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public void setToDefault() {
        setX(2);
        setY(2);
        save();
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public String getName() {
        return "Coordinate";
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);
        drawCoordPane();
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
        if (getToggled() == 0) {
            BH.drawShadowedFont("X", getX() - 3, getY() - 3, 0xFFFFFF);
        }
    }

    private void drawCoordPane() {
        double x = Math.floor(mc.thePlayer.posX);
        double y = Math.floor(mc.thePlayer.posY - 1);
        double z = Math.floor(mc.thePlayer.posZ);

        double rotation = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);

        int width;
        if (x < 1000 && y < 1000 && z < 1000 && x > -1000 && y > -1000 && z > -1000)
            width = 54;
        else if (x > -10000 && y > -10000 & z > -10000 && x < 10000 && y < 10000 && z < 10000)
            width = 60;
        else if (x > -100000 && y > -100000 && z > -100000 && x < 100000 && y < 100000 && z < 100000)
            width = 66;
        else if (x > -1000000 && y > -1000000 && z > -1000000 && x < 1000000 && y < 1000000 && z < 1000000)
            width = 72;
        else if (x > -10000000 && y > -10000000 && z > -10000000 && x < 10000000 && y < 10000000 && z < 10000000)
            width = 78;
        else if (x > -100000000 && y > -100000000 && z > -100000000 && x < 100000000 && y < 100000000 && z < 100000000)
            width = 98;
        else
            width = 86;

        this.width = width;
        BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, width, this.height, r, g, b, a, r1, g1, b1, a1, thickness);
        GlStateManager.enableTexture2D();

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
        } else if ((rotation > 157.5 && rotation <= 202.5) || (rotation > -180 && rotation <= -157.5)) {
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

        FR.drawString("X: " + (int) x, this.x, this.y, 0xffffff);
        FR.drawString("Y: " + (int) y, this.x, this.y + 10, 0xffffff);
        FR.drawString("Z: " + (int) z, this.x, this.y + 20, 0xffffff);

        int tempx = getX() + getWidth() - 12;
        int dLength = mc.fontRendererObj.getStringWidth(this.direction) / 2;

        FR.drawString(xEx, tempx, this.y, 0xffffff);
        FR.drawString(this.direction, tempx - dLength + 3, this.y + 10, 0xffffff);
        FR.drawString(zEx, tempx, this.y + 20, 0xffffff);

        GlStateManager.disableTexture2D();
        xEx = "";
        zEx = "";
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
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) r);
        data.add((double) g);
        data.add((double) b);
        data.add((double) a);
        data.add((double) r1);
        data.add((double) g1);
        data.add((double) b1);
        data.add((double) a1);
        data.add(thickness);
        data.add(toggle);
        FM.setArray(data);
    }

    @Override
    public void load() {
        data.clear();
        data = FM.getArray();
        if (data.size() < 12) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            r = data.get(2).intValue();
            g = data.get(3).intValue();
            b = data.get(4).intValue();
            a = data.get(5).intValue();
            r1 = data.get(6).intValue();
            g1 = data.get(7).intValue();
            b1 = data.get(8).intValue();
            a1 = data.get(9).intValue();
            thickness = data.get(10);
            toggle = data.get(11);
        }
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
        return r;
    }

    @Override
    public int getG() {
        return g;
    }

    @Override
    public int getB() {
        return b;
    }

    @Override
    public int getA() {
        return a;
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
        return r1;
    }

    @Override
    public int getBorderG() {
        return g1;
    }

    @Override
    public int getBorderB() {
        return b1;
    }

    @Override
    public int getBorderA() {
        return a1;
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
