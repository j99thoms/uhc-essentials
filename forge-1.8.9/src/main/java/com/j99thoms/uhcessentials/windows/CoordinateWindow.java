package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

import com.j99thoms.uhcessentials.HUDGraphics;

public class CoordinateWindow extends ThemedWindow {
    private final Minecraft mc;

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 2;

    private double coordX;
    private double coordY;
    private double coordZ;
    private String direction = "";
    private String xSign = "";
    private String zSign = "";

    private int width;
    private int height = 30;

    private final FileManager fileManager;
    private ArrayList<Double> data = new ArrayList<Double>();

    public CoordinateWindow(HUDGraphics hudGraphics, Minecraft mc, WindowTheme theme) {
        super(hudGraphics, theme);
        this.mc = mc;
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("CoordPos", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows your position`Right click to change colors";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
        coordX = Math.floor(mc.thePlayer.posX);
        coordY = Math.floor(mc.thePlayer.posY);
        coordZ = Math.floor(mc.thePlayer.posZ);

        if (coordX < 1000 && coordY < 1000 && coordZ < 1000 && coordX > -1000 && coordY > -1000 && coordZ > -1000)
            width = 54;
        else if (coordX > -10000 && coordY > -10000 && coordZ > -10000 && coordX < 10000 && coordY < 10000 && coordZ < 10000)
            width = 60;
        else if (coordX > -100000 && coordY > -100000 && coordZ > -100000 && coordX < 100000 && coordY < 100000 && coordZ < 100000)
            width = 66;
        else if (coordX > -1000000 && coordY > -1000000 && coordZ > -1000000 && coordX < 1000000 && coordY < 1000000 && coordZ < 1000000)
            width = 72;
        else if (coordX > -10000000 && coordY > -10000000 && coordZ > -10000000 && coordX < 10000000 && coordY < 10000000 && coordZ < 10000000)
            width = 78;
        else if (coordX > -100000000 && coordY > -100000000 && coordZ > -100000000 && coordX < 100000000 && coordY < 100000000 && coordZ < 100000000)
            width = 98;
        else
            width = 86;

        double rotation = MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);

        xSign = "";
        zSign = "";
        
        if (rotation > -22.5 && rotation <= 22.5) {
            direction = "S";
            zSign = "+";
        } else if (rotation > 22.5 && rotation <= 67.5) {
            direction = "SW";
            zSign = "+";
            xSign = "-";
        } else if (rotation > 67.5 && rotation <= 112.5) {
            direction = "W";
            xSign = "-";
        } else if (rotation > 112.5 && rotation <= 157.5) {
            direction = "NW";
            zSign = "-";
            xSign = "-";
        } else if ((rotation > 157.5 && rotation <= 202.5) || (rotation > -180 && rotation <= -157.5)) {
            direction = "N";
            zSign = "-";
        } else if (rotation > -157.5 && rotation <= -112.5) {
            direction = "NE";
            zSign = "-";
            xSign = "+";
        } else if (rotation > -112.5 && rotation <= -67.5) {
            direction = "E";
            xSign = "+";
        } else if (rotation > -67.5 && rotation <= -22.5) {
            direction = "SE";
            zSign = "+";
            xSign = "+";
        }
    }

    @Override
    public void render() {
        hudGraphics.drawHUDRectWithBorder(this.x - 1, this.y - 1, width, this.height,
                getR(), getG(), getB(), getA(),
                getBorderR(), getBorderG(), getBorderB(), getBorderA(),
                getThickness());

        hudGraphics.drawFont("X: " + (int) coordX, this.x, this.y, 0xffffff);
        hudGraphics.drawFont("Y: " + (int) coordY, this.x, this.y + 10, 0xffffff);
        hudGraphics.drawFont("Z: " + (int) coordZ, this.x, this.y + 20, 0xffffff);

        int tempx = getX() + getWidth() - 12;
        int dLength = hudGraphics.getStringWidth(direction) / 2;

        hudGraphics.drawFont(xSign, tempx, this.y, 0xffffff);
        hudGraphics.drawFont(direction, tempx - dLength + 3, this.y + 10, 0xffffff);
        hudGraphics.drawFont(zSign, tempx, this.y + 20, 0xffffff);

        if (getToggled() == 0) {
            hudGraphics.drawShadowedFont("X", getX() - 3, getY() - 3, 0xFFFFFF);
        }
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
            toggle = data.get(2).intValue();
        }
    }

}
