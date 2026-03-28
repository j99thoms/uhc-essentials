package com.j99thoms.uhcessentials.windows;

public abstract class BaseWindow {

    public abstract void update();

    public abstract void render();

    public abstract void setToDefault();

    public abstract void setX(int x);

    public abstract void setY(int y);

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setRGBA(int red, int green, int blue, int alpha);

    public abstract int getR();

    public abstract int getG();

    public abstract int getB();

    public abstract int getA();

    public abstract void setBorderRGB(int red, int green, int blue, int alpha);

    public abstract int getBorderR();

    public abstract int getBorderG();

    public abstract int getBorderB();

    public abstract int getBorderA();

    public abstract void setThickness(float thickness);

    public abstract double getThickness();

    public abstract void save();

    public abstract void load();

    public abstract void toggle();

    public abstract int getToggled();

    public abstract String getToolTip();

    public boolean isThemed() {
        return false;
    }
}
