package com.j99thoms.uhcessentials.windows;

public interface Themeable {
    WindowTheme getTheme();
    void save();

    default void setRGBA(int r, int g, int b, int a) { getTheme().setRGBA(r, g, b, a); }
    default int getR()       { return getTheme().getR(); }
    default int getG()       { return getTheme().getG(); }
    default int getB()       { return getTheme().getB(); }
    default int getA()       { return getTheme().getA(); }
    default void setBorderRGB(int r, int g, int b, int a) { getTheme().setBorderRGB(r, g, b, a); }
    default int getBorderR() { return getTheme().getBorderR(); }
    default int getBorderG() { return getTheme().getBorderG(); }
    default int getBorderB() { return getTheme().getBorderB(); }
    default int getBorderA() { return getTheme().getBorderA(); }
    default void setThickness(float t) { getTheme().setThickness(t); }
    default double getThickness()      { return getTheme().getThickness(); }
}
