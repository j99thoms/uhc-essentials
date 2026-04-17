package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.util.FileManager;

public class CompassWindow extends BaseWindow {

    private static final int DEFAULT_X = 1;
    private static final int DEFAULT_Y = 68;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    public CompassWindow(HUDGraphics hudGraphics) {
        super(hudGraphics);
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("Compass", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Points to 0, 0 (same with compass item disable to fix normal compass)`/compass <x> <y> in chat to change direction";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        hudGraphics.drawItemSprite(x, y, "minecraft:compass");
        if (getToggled() == 0)
            hudGraphics.drawShadowedFont("X", x, y, 0xffffffff);
    }

    @Override
    public int getWidth() {
        return 14;
    }

    @Override
    public int getHeight() {
        return 12;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) toggle);
        fileManager.setArray(data);
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
