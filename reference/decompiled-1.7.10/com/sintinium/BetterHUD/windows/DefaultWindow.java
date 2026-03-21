/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bdw
 */
package com.sintinium.BetterHUD.windows;

public abstract class DefaultWindow
extends bdw {
    public abstract void update();

    public abstract void render();

    public abstract void setToDefault();

    public abstract void setX(int var1);

    public abstract void setY(int var1);

    public abstract int getX();

    public abstract int getY();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract void setRGBA(int var1, int var2, int var3, int var4);

    public abstract int getR();

    public abstract int getG();

    public abstract int getB();

    public abstract int getA();

    public abstract void setBorderRGB(int var1, int var2, int var3, int var4);

    public abstract int getBorderR();

    public abstract int getBorderG();

    public abstract int getBorderB();

    public abstract int getBorderA();

    public abstract void setThickness(float var1);

    public abstract double getThickness();

    public abstract void save();

    public abstract void load();

    public abstract String getName();

    public abstract void toggle();

    public abstract int getToggled();

    public abstract String getToolTip();
}
