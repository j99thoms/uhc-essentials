package com.j99thoms.uhcessentials.windows;

public interface Colorizable {

    void setRGBA(int red, int green, int blue, int alpha);

    int getR();

    int getG();

    int getB();

    int getA();

    void setBorderRGB(int red, int green, int blue, int alpha);

    int getBorderR();

    int getBorderG();

    int getBorderB();

    int getBorderA();

    void setThickness(float thickness);

    double getThickness();

    void save();
}
