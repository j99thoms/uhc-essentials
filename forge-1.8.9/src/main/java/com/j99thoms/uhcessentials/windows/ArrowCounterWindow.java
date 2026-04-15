package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.GameContext;
import com.j99thoms.uhcessentials.HUDGraphics;

public class ArrowCounterWindow extends BaseWindow {

    private static final int DEFAULT_X = 16;
    private static final int DEFAULT_Y = 68;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;
    private boolean shouldFlash = true;
    private boolean flashVisible = false;
    private int count = 0;
    private int timer = 0;
    private long lastTime;

    private final GameContext gameContext;

    public ArrowCounterWindow(HUDGraphics hudGraphics, GameContext gameContext) {
        super(hudGraphics);
        this.gameContext = gameContext;
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("Arrow", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how many arrows you have. Click to change modes";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : (toggle == 1 ? 2 : 0);
    }

    @Override
    public int getToggled() {
        if (toggle == 2) {
            return 1;
        }
        return (int) toggle;
    }

    @Override
    public void update() {
        count = gameContext.getArrowCount();

        long now = System.currentTimeMillis();
        flashVisible = false;
        if (count <= 5 && shouldFlash) {
            if (timer < 3 && now - lastTime >= 500L) {
                if (now - lastTime >= 1000L) {
                    lastTime = now;
                    timer++;
                } else {
                    flashVisible = true;
                }
            } else if (timer >= 3) {
                shouldFlash = false;
            }
        } else if (count > 5) {
            timer = 0;
            shouldFlash = true;
            lastTime = now;
        }
    }

    @Override
    public void render() {
        hudGraphics.drawItemSprite(x, y, "minecraft:arrow");

        if ((int) toggle != 2 || count < 64)
            hudGraphics.drawShadowedFont(count + "", x + 11, y + 9, 0xffffffff);
        else {
            int stacks = (count / 64);
            int remainder = count - stacks * 64;
            String remainderInfo = remainder == 0 ? "" : "+" + remainder;
            hudGraphics.drawShadowedFont("[" + stacks + "]" + remainderInfo, x + 11, y + 9, 0xffffffff);
        }

        if (flashVisible)
            hudGraphics.drawHUDRectWithBorder(getX() + 2, getY() + 2, getWidth() + 1, getHeight() + 1,
                255, 0, 0, 150,
                0, 0, 0, 100,
                .5);

        if (getToggled() == 0)
            hudGraphics.drawShadowedFont("X", x, y, 0xffffffff);
        else if (HUDConfigScreen.guiOpen) {
            String mode = (int) toggle == 1 ? "Sum" : "Stacks";
            hudGraphics.drawShadowedFont(mode, x, y, 0xffffffff);
        }
    }

    @Override
    public int getWidth() {
        return 13;
    }

    @Override
    public int getHeight() {
        return 13;
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
