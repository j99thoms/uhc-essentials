package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;

import com.j99thoms.uhcessentials.HUDGraphics;

public class ClockWindow extends BaseWindow {

    private static final int DEFAULT_X = 36;
    private static final int DEFAULT_Y = 68;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final Minecraft mc;

    public ClockWindow(HUDGraphics hudGraphics, Minecraft mc) {
        super(hudGraphics);
        this.mc = mc;
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("Clock", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "I wonder if it's day out..";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
        hudGraphics.drawItemSprite(x, y, Items.clock);
        if (getToggled() == 0)
            hudGraphics.drawShadowedFont("X", x, y, 0xffffffff);
    }

    @Override
    public void render() {
    }

    @Override
    public int getWidth() {
        return 15;
    }

    @Override
    public int getHeight() {
        return 15;
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
