package com.j99thoms.uhcessentials.windows;

import com.j99thoms.uhcessentials.HUDGraphics;

public abstract class BaseWindow {

    protected final HUDGraphics hudGraphics;
    protected int x;
    protected int y;
    protected double toggle = 1;

    protected BaseWindow(HUDGraphics hudGraphics) {
        this.hudGraphics = hudGraphics;
    }

    public abstract void update();

    public abstract void render();

    public abstract void setToDefault();

    public void setX(int x) { this.x = x; }

    public void setY(int y) { this.y = y; }

    public int getX() { return x; }

    public int getY() { return y; }

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void save();

    public abstract void load();

    public void toggle() { toggle = toggle == 0 ? 1 : 0; }

    public int getToggled() { return (int) toggle; }

    public abstract String getToolTip();

}
