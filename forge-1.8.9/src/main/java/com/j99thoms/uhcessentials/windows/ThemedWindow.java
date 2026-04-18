package com.j99thoms.uhcessentials.windows;

import com.j99thoms.uhcessentials.api.HUDGraphics;

public abstract class ThemedWindow extends BaseWindow {

    protected final WindowTheme theme;

    protected ThemedWindow(HUDGraphics hudGraphics, WindowTheme theme) {
        super(hudGraphics);
        this.theme = theme;
    }

    public void setRGBA(int red, int green, int blue, int alpha) {
        theme.setRGBA(red, green, blue, alpha);
    }

    public int getR() {
        return theme.getR();
    }

    public int getG() {
        return theme.getG();
    }

    public int getB() {
        return theme.getB();
    }

    public int getA() {
        return theme.getA();
    }

    public void setBorderRGB(int red, int green, int blue, int alpha) {
        theme.setBorderRGB(red, green, blue, alpha);
    }

    public int getBorderR() {
        return theme.getBorderR();
    }

    public int getBorderG() {
        return theme.getBorderG();
    }

    public int getBorderB() {
        return theme.getBorderB();
    }

    public int getBorderA() {
        return theme.getBorderA();
    }

    public void setThickness(float thickness) {
        theme.setThickness(thickness);
    }

    public double getThickness() {
        return theme.getThickness();
    }
}
