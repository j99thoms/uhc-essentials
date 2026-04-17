package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.api.GameContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.util.FileManager;

public class CoordinateWindow extends ThemedWindow {
    private final GameContext gameContext;

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 2;

    private double coordX;
    private double coordY;
    private double coordZ;
    private String xLine = "X: ";
    private String yLine = "Y: ";
    private String zLine = "Z: ";
    private String direction = "";
    private String xSign = "";
    private String zSign = "";

    private int width;
    private int height = 30;

    private final FileManager fileManager;
    private ArrayList<Double> data = new ArrayList<Double>();

    public CoordinateWindow(HUDGraphics hudGraphics, GameContext gameContext, WindowTheme theme) {
        super(hudGraphics, theme);
        this.gameContext = gameContext;
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
        coordX = Math.floor(gameContext.getPlayerX());
        coordY = Math.floor(gameContext.getPlayerY());
        coordZ = Math.floor(gameContext.getPlayerZ());

        xLine = "X: " + (int) coordX;
        yLine = "Y: " + (int) coordY;
        zLine = "Z: " + (int) coordZ;
        int contentWidth = Math.max(hudGraphics.getStringWidth(xLine),
                           Math.max(hudGraphics.getStringWidth(yLine),
                                    hudGraphics.getStringWidth(zLine)));
        this.width = contentWidth + 30; // +30 for the direction column on the right

        double rotation = gameContext.getPlayerFacingYaw();

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

        hudGraphics.drawFont(xLine, this.x, this.y, 0xffffff);
        hudGraphics.drawFont(yLine, this.x, this.y + 10, 0xffffff);
        hudGraphics.drawFont(zLine, this.x, this.y + 20, 0xffffff);

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
