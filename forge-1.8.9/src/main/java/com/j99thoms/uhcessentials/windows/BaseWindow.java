package com.j99thoms.uhcessentials.windows;

import com.j99thoms.uhcessentials.HUDGraphics;

public abstract class BaseWindow {

    protected final HUDGraphics hudGraphics;

    protected BaseWindow(HUDGraphics hudGraphics) {
        this.hudGraphics = hudGraphics;
    }


    public abstract void update();

    public abstract void render();

    public abstract void setToDefault();

    public abstract void setX(int x);

    public abstract void setY(int y);

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void save();

    public abstract void load();

    public abstract void toggle();

    public abstract int getToggled();

    public abstract String getToolTip();

}
