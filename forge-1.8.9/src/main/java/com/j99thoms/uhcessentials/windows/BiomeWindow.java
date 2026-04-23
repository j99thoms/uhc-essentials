package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.api.GameContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.util.FileManager;

public class BiomeWindow extends BaseWindow implements Themeable {

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 46;

    private String biomeName = "";
    private int width = 0;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final GameContext gameContext;
    private final WindowTheme theme;

    public BiomeWindow(HUDGraphics hudGraphics, GameContext gameContext, WindowTheme theme) {
        super(hudGraphics);
        this.theme = theme;
        this.gameContext = gameContext;
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("BiomeWindow", 3);
        load();
    }

    @Override
    public WindowTheme getTheme() { return theme; }

    @Override
    public String getToolTip() {
        return "Shows what biome you're currently in`Right click to change colors";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
        biomeName = gameContext.getBiomeName();
        width = hudGraphics.getStringWidth(biomeName);
    }

    @Override
    public void render() {
        hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, width + 1, getHeight(),
                getR(), getG(), getB(), getA(),
                getBorderR(), getBorderG(), getBorderB(), getBorderA(),
                getThickness());
        hudGraphics.drawShadowedFont(biomeName, x, y, 0xffffffff);

        if (getToggled() == 0)
            hudGraphics.drawShadowedFont("X", getX() - 5, getY() - 5, 0xffffffff);
    }

    @Override
    public int getWidth() {
        return width + 1;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) toggle);
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
