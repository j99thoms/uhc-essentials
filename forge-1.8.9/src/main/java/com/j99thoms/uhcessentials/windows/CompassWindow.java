package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;

import com.j99thoms.uhcessentials.HUDGraphics;

public class CompassWindow extends BaseWindow {

    private static final int DEFAULT_X = 1;
    private static final int DEFAULT_Y = 68;

    private int x = DEFAULT_X;
    private int y = DEFAULT_Y;

    private int width = 0;
    private int height = 0;

    private double toggle = 1;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final Minecraft mc;

    public CompassWindow(HUDGraphics hudGraphics, Minecraft mc) {
        super(hudGraphics);
        this.mc = mc;
        fileManager = new FileManager("Compass", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Points to 0, 0 (same with compass item disable to fix normal compass)`/compass <x> <y> in chat to change direction";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        hudGraphics.drawItemSprite(x, y, Items.compass);
        if ((int) toggle == 0)
            mc.fontRendererObj.drawStringWithShadow("X", x, y, 0xffffffff);
    }

    @Override
    public void render() {
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
        return x;
    }

    @Override
    public int getY() {
        return y;
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
