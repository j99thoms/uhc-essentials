package com.j99thoms.uhcessentials;

public interface HUDGraphics {

    void drawHUDRect(double x, double y, double width, double height,
                     double red, double green, double blue, double alpha);

    void drawHUDRectWithBorder(double x, double y, double width, double height,
                                double red, double green, double blue, double alpha,
                                double red2, double green2, double blue2, double alpha2,
                                double thickness);

    void drawHUDRectWithBorder(double x, double y, double width, double height,
                                double red, double green, double blue, double alpha,
                                double red2, double green2, double blue2, double alpha2,
                                double thickness,
                                boolean left, boolean right, boolean top, boolean bottom);

    void drawHUDRectBorder(double x, double y, double width, double height,
                           double red, double green, double blue, double alpha,
                           double thickness);

    void drawFont(String text, int x, int y, int color);

    void drawShadowedFont(String text, int x, int y, int color);

    void drawItemSprite(int xPos, int yPos, String resourceLocation);

    int getStringWidth(String text);
}
