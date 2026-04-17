package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.util.FileManager;

public class WindowTheme {

    private int fillRed = 69;
    private int fillGreen = 69;
    private int fillBlue = 69;
    private int fillAlpha = 150;

    private int borderRed = 0;
    private int borderGreen = 0;
    private int borderBlue = 0;
    private int borderAlpha = 255;

    private double thickness = 0.8;

    private final FileManager fileManager;
    private ArrayList<Double> data = new ArrayList<Double>();

    public WindowTheme(String name) {
        fileManager = new FileManager(name, 9);
        load();
    }

    public void save() {
        data.clear();
        data.add((double) fillRed);
        data.add((double) fillGreen);
        data.add((double) fillBlue);
        data.add((double) fillAlpha);
        data.add((double) borderRed);
        data.add((double) borderGreen);
        data.add((double) borderBlue);
        data.add((double) borderAlpha);
        data.add(thickness);
        fileManager.setArray(data);
    }

    public void load() {
        data.clear();
        data = fileManager.getArray();
        if (data.size() < 9) {
            save();
        } else {
            fillRed = data.get(0).intValue();
            fillGreen = data.get(1).intValue();
            fillBlue = data.get(2).intValue();
            fillAlpha = data.get(3).intValue();
            borderRed = data.get(4).intValue();
            borderGreen = data.get(5).intValue();
            borderBlue = data.get(6).intValue();
            borderAlpha = data.get(7).intValue();
            thickness = data.get(8);
        }
    }

    public void setRGBA(int red, int green, int blue, int alpha) {
        this.fillRed = red;
        this.fillGreen = green;
        this.fillBlue = blue;
        this.fillAlpha = alpha;
    }

    public void setBorderRGB(int red, int green, int blue, int alpha) {
        this.borderRed = red;
        this.borderGreen = green;
        this.borderBlue = blue;
        this.borderAlpha = alpha;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }

    public int getR() { return fillRed; }
    public int getG() { return fillGreen; }
    public int getB() { return fillBlue; }
    public int getA() { return fillAlpha; }
    public int getBorderR() { return borderRed; }
    public int getBorderG() { return borderGreen; }
    public int getBorderB() { return borderBlue; }
    public int getBorderA() { return borderAlpha; }
    public double getThickness() { return thickness; }
}
