package com.j99thoms.uhcessentials.windows;

public abstract class ThemedWindow extends BaseWindow implements Colorizable {

    protected final WindowTheme theme;

    protected ThemedWindow(WindowTheme theme) {
        this.theme = theme;
    }

    @Override
    public void setRGBA(int red, int green, int blue, int alpha) {
        theme.setRGBA(red, green, blue, alpha);
    }

    @Override
    public int getR() {
        return theme.getR();
    }

    @Override
    public int getG() {
        return theme.getG();
    }

    @Override
    public int getB() {
        return theme.getB();
    }

    @Override
    public int getA() {
        return theme.getA();
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
        theme.setBorderRGB(red, green, blue, alpha);
    }

    @Override
    public int getBorderR() {
        return theme.getBorderR();
    }

    @Override
    public int getBorderG() {
        return theme.getBorderG();
    }

    @Override
    public int getBorderB() {
        return theme.getBorderB();
    }

    @Override
    public int getBorderA() {
        return theme.getBorderA();
    }

    @Override
    public void setThickness(float thickness) {
        theme.setThickness(thickness);
    }

    @Override
    public double getThickness() {
        return theme.getThickness();
    }
}
