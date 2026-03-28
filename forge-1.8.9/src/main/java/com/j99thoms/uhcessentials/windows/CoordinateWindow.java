package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import com.j99thoms.uhcessentials.BetterHUD;

public class CoordinateWindow extends BaseWindow implements Colorizable {

    private final BetterHUD betterHUD;
    private final FontRenderer fontRenderer;
    private final Minecraft mc;
    private final WindowTheme theme;

    private int x = 2;
    private int y = 2;

    private String direction;
    private String xSign;
    private String zSign;

    private double toggle = 1;

    private int width;
    private int height = 30;
    float scale = .75F;

    private final FileManager fileManager;
    private ArrayList<Double> data = new ArrayList<Double>();

    public CoordinateWindow(BetterHUD betterHUD, FontRenderer fontRenderer, Minecraft mc, WindowTheme theme) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.fontRenderer = fontRenderer;
        this.theme = theme;
        fileManager = new FileManager("CoordPos", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows your position`Right click to change colors";
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
            betterHUD.drawShadowedFont("X", getX() - 3, getY() - 3, 0xFFFFFF);
        }
    }

    private void drawCoordPane() {
        double x = Math.floor(mc.thePlayer.posX);
        double y = Math.floor(mc.thePlayer.posY);
        double z = Math.floor(mc.thePlayer.posZ);

        double rotation = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);

        int width;
        if (x < 1000 && y < 1000 && z < 1000 && x > -1000 && y > -1000 && z > -1000)
            width = 54;
        else if (x > -10000 && y > -10000 && z > -10000 && x < 10000 && y < 10000 && z < 10000)
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
        betterHUD.drawHUDRectWithBorder(this.x - 1, this.y - 1, width, this.height,
                theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                theme.getThickness());
        GlStateManager.enableTexture2D();

        if (rotation > -22.5 && rotation <= 22.5) {
            this.direction = "S";
            this.zSign = "+";
        } else if (rotation > 22.5 && rotation <= 67.5) {
            this.direction = "SW";
            this.zSign = "+";
            this.xSign = "-";
        } else if (rotation > 67.5 && rotation <= 112.5) {
            this.direction = "W";
            this.xSign = "-";
        } else if (rotation > 112.5 && rotation <= 157.5) {
            this.direction = "NW";
            this.zSign = "-";
            this.xSign = "-";
        } else if ((rotation > 157.5 && rotation <= 202.5) || (rotation > -180 && rotation <= -157.5)) {
            this.direction = "N";
            this.zSign = "-";
        } else if (rotation > -157.5 && rotation <= -112.5) {
            this.direction = "NE";
            this.zSign = "-";
            this.xSign = "+";
        } else if (rotation > -112.5 && rotation <= -67.5) {
            this.direction = "E";
            this.xSign = "+";
        } else if (rotation > -67.5 && rotation <= -22.5) {
            this.direction = "SE";
            this.zSign = "+";
            this.xSign = "+";
        }

        fontRenderer.drawString("X: " + (int) x, this.x, this.y, 0xffffff);
        fontRenderer.drawString("Y: " + (int) y, this.x, this.y + 10, 0xffffff);
        fontRenderer.drawString("Z: " + (int) z, this.x, this.y + 20, 0xffffff);

        int tempx = getX() + getWidth() - 12;
        int dLength = mc.fontRendererObj.getStringWidth(this.direction) / 2;

        fontRenderer.drawString(xSign, tempx, this.y, 0xffffff);
        fontRenderer.drawString(this.direction, tempx - dLength + 3, this.y + 10, 0xffffff);
        fontRenderer.drawString(zSign, tempx, this.y + 20, 0xffffff);

        GlStateManager.disableTexture2D();
        xSign = "";
        zSign = "";
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
        data.add(toggle);
        fileManager.setArray(data);
        theme.save();
    }

    @Override
    public void load() {
        data.clear();
        data = fileManager.getArray();
        if (data.size() < 3) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            toggle = data.get(2);
        }
    }

    @Override
    public void setRGBA(int red, int green, int blue, int alpha) {
        theme.setRGBA(red, green, blue, alpha);
    }

    @Override
    public int getR() {
        return theme.getR();
    }

    @Override
    public int getG() {
        return theme.getG();
    }

    @Override
    public int getB() {
        return theme.getB();
    }

    @Override
    public int getA() {
        return theme.getA();
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
        theme.setBorderRGB(red, green, blue, alpha);
    }

    @Override
    public int getBorderR() {
        return theme.getBorderR();
    }

    @Override
    public int getBorderG() {
        return theme.getBorderG();
    }

    @Override
    public int getBorderB() {
        return theme.getBorderB();
    }

    @Override
    public int getBorderA() {
        return theme.getBorderA();
    }

    @Override
    public void setThickness(float thickness) {
        theme.setThickness(thickness);
    }

    @Override
    public double getThickness() {
        return theme.getThickness();
    }
}
