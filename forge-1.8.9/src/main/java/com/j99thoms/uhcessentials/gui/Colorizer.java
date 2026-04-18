package com.j99thoms.uhcessentials.gui;

import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.windows.ThemedWindow;

public class Colorizer {

    private final HUDGraphics hudGraphics;
    private final ThemedWindow window;
    private final GuiContext guiContext;

    private int fillRed;
    private int fillGreen;
    private int fillBlue;
    private int fillAlpha;
    private int borderRed;
    private int borderGreen;
    private int borderBlue;
    private int borderAlpha;
    private int x;
    private int y;
    private int dx;
    private int lastX;

    private int redKnobX;
    private int redKnobY;
    private int greenKnobX;
    private int greenKnobY;
    private int blueKnobX;
    private int blueKnobY;
    private int alphaKnobX;
    private int alphaKnobY;
    private boolean grabbedRed = false;
    private boolean grabbedGreen = false;
    private boolean grabbedBlue = false;
    private boolean grabbedAlpha = false;
    private boolean border = false;

    public Colorizer(HUDGraphics hudGraphics, ThemedWindow window, GuiContext guiContext) {
        this.hudGraphics = hudGraphics;
        this.window = window;
        this.guiContext = guiContext;
        getInts();
    }

    public void update() {
        getInts();
        mouse();
        render();
    }

    private static int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }

    private void getInts() {
        fillRed = window.getR();
        fillGreen = window.getG();
        fillBlue = window.getB();
        fillAlpha = window.getA();
        borderRed = window.getBorderR();
        borderGreen = window.getBorderG();
        borderBlue = window.getBorderB();
        borderAlpha = window.getBorderA();
    }

    private void mouse() {
        if (guiContext.isMouseButtonDown(0)) {
            x = guiContext.getMouseX();
            y = guiContext.getMouseY();
            dx = x - lastX;
            lastX = x;
            if (!border) {
                if ((y < redKnobY + 7 && y >= redKnobY && x < redKnobX + 5 && x >= redKnobX) || grabbedRed) {
                    grabbedRed = true;
                    window.setRGBA(clamp(fillRed + dx), fillGreen, fillBlue, fillAlpha);
                } else if ((y < greenKnobY + 7 && y >= greenKnobY && x < greenKnobX + 5 && x >= greenKnobX) || grabbedGreen) {
                    grabbedGreen = true;
                    window.setRGBA(fillRed, clamp(fillGreen + dx), fillBlue, fillAlpha);
                } else if ((y < blueKnobY + 7 && y >= blueKnobY && x < blueKnobX + 5 && x >= blueKnobX) || grabbedBlue) {
                    grabbedBlue = true;
                    window.setRGBA(fillRed, fillGreen, clamp(fillBlue + dx), fillAlpha);
                } else if ((y < alphaKnobY + 7 && y >= alphaKnobY && x < alphaKnobX + 5 && x >= alphaKnobX) || grabbedAlpha) {
                    grabbedAlpha = true;
                    window.setRGBA(fillRed, fillGreen, fillBlue, clamp(fillAlpha + dx));
                } else if (x <= guiContext.getScreenWidth() / 2 + 150 + 12 && x >= guiContext.getScreenWidth() / 2 + 150
                        && y <= guiContext.getScreenHeight() / 2 - 25 + 60 + 9 && y >= guiContext.getScreenHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= guiContext.getScreenWidth() / 2 + 150 + 12 + 21 && x >= guiContext.getScreenWidth() / 2 + 150
                        && y <= guiContext.getScreenHeight() / 2 - 25 + 70 + 9 && y >= guiContext.getScreenHeight() / 2 - 25 + 70) {
                    border = true;
                }
            } else {
                if ((y < redKnobY + 7 && y >= redKnobY && x < redKnobX + 5 && x >= redKnobX) || grabbedRed) {
                    grabbedRed = true;
                    window.setBorderRGB(clamp(borderRed + dx), borderGreen, borderBlue, borderAlpha);
                } else if ((y < greenKnobY + 7 && y >= greenKnobY && x < greenKnobX + 5 && x >= greenKnobX) || grabbedGreen) {
                    grabbedGreen = true;
                    window.setBorderRGB(borderRed, clamp(borderGreen + dx), borderBlue, borderAlpha);
                } else if ((y < blueKnobY + 7 && y >= blueKnobY && x < blueKnobX + 5 && x >= blueKnobX) || grabbedBlue) {
                    grabbedBlue = true;
                    window.setBorderRGB(borderRed, borderGreen, clamp(borderBlue + dx), borderAlpha);
                } else if ((y < alphaKnobY + 7 && y >= alphaKnobY && x < alphaKnobX + 5 && x >= alphaKnobX) || grabbedAlpha) {
                    grabbedAlpha = true;
                    window.setBorderRGB(borderRed, borderGreen, borderBlue, clamp(borderAlpha + dx));
                } else if (x <= guiContext.getScreenWidth() / 2 + 150 + 12 && x >= guiContext.getScreenWidth() / 2 + 150
                        && y <= guiContext.getScreenHeight() / 2 - 25 + 60 + 9 && y >= guiContext.getScreenHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= guiContext.getScreenWidth() / 2 + 150 + 12 + 21 && x >= guiContext.getScreenWidth() / 2 + 150
                        && y <= guiContext.getScreenHeight() / 2 - 25 + 70 + 9 && y >= guiContext.getScreenHeight() / 2 - 25 + 70) {
                    border = true;
                }
            }
            window.save();
        } else {
            grabbedRed = false;
            grabbedGreen = false;
            grabbedBlue = false;
            grabbedAlpha = false;
            lastX = guiContext.getMouseX();
        }
    }

    private void render() {
        if (!border) {
            redKnobX = guiContext.getScreenWidth() / 2 - 127 + fillRed;
            redKnobY = guiContext.getScreenHeight() / 2 - 2 - 32;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 - 32, "Red");
            renderKnob(redKnobX, redKnobY, "red");
            greenKnobX = guiContext.getScreenWidth() / 2 - 127 + fillGreen;
            greenKnobY = guiContext.getScreenHeight() / 2 - 2;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2, "Green");
            renderKnob(greenKnobX, greenKnobY, "green");
            blueKnobX = guiContext.getScreenWidth() / 2 - 127 + fillBlue;
            blueKnobY = guiContext.getScreenHeight() / 2 - 2 + 32;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 + 32, "Blue");
            renderKnob(blueKnobX, blueKnobY, "blue");
            alphaKnobX = guiContext.getScreenWidth() / 2 - 127 + fillAlpha;
            alphaKnobY = guiContext.getScreenHeight() / 2 - 2 + 64;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(alphaKnobX, alphaKnobY, "alpha");
        } else {
            redKnobX = guiContext.getScreenWidth() / 2 - 127 + borderRed;
            redKnobY = guiContext.getScreenHeight() / 2 - 2 - 32;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 - 32, "Red");
            renderKnob(redKnobX, redKnobY, "red");
            greenKnobX = guiContext.getScreenWidth() / 2 - 127 + borderGreen;
            greenKnobY = guiContext.getScreenHeight() / 2 - 2;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2, "Green");
            renderKnob(greenKnobX, greenKnobY, "green");
            blueKnobX = guiContext.getScreenWidth() / 2 - 127 + borderBlue;
            blueKnobY = guiContext.getScreenHeight() / 2 - 2 + 32;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 + 32, "Blue");
            renderKnob(blueKnobX, blueKnobY, "blue");
            alphaKnobX = guiContext.getScreenWidth() / 2 - 127 + borderAlpha;
            alphaKnobY = guiContext.getScreenHeight() / 2 - 2 + 64;
            renderSlider(guiContext.getScreenWidth() / 2 - 127, guiContext.getScreenHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(alphaKnobX, alphaKnobY, "alpha");
        }
        hudGraphics.drawHUDRectWithBorder(guiContext.getScreenWidth() / 2 + 150, guiContext.getScreenHeight() / 2 - 25, 50, 50,
                fillRed, fillGreen, fillBlue, fillAlpha,
                borderRed, borderGreen, borderBlue, borderAlpha, window.getThickness());
        int px = guiContext.getScreenWidth() / 2 + 150 - 1;
        int py = guiContext.getScreenHeight() / 2 - 25 + 60;
        int pwidth = 13;
        int pheight = 9;
        if (!border) {
            hudGraphics.drawHUDRectWithBorder(px, py, pwidth, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        } else {
            hudGraphics.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py, pwidth, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        }
        hudGraphics.drawShadowedFont("BG", guiContext.getScreenWidth() / 2 + 150, guiContext.getScreenHeight() / 2 - 25 + 60, 0xFFFFFF);
        hudGraphics.drawShadowedFont("Outline", guiContext.getScreenWidth() / 2 + 150, guiContext.getScreenHeight() / 2 - 25 + 70, 0xFFFFFF);
    }

    private void renderSlider(int x, int y, String effector) {
        hudGraphics.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        y -= 10;
        if (!border) {
            if (effector.equalsIgnoreCase("Red")) {
                hudGraphics.drawShadowedFont(effector + ": " + fillRed, x, y, -1);
            } else if (effector.equalsIgnoreCase("Green")) {
                hudGraphics.drawShadowedFont(effector + ": " + fillGreen, x, y, -1);
            } else if (effector.equalsIgnoreCase("Blue")) {
                hudGraphics.drawShadowedFont(effector + ": " + fillBlue, x, y, -1);
            } else if (effector.equalsIgnoreCase("Alpha")) {
                hudGraphics.drawShadowedFont(effector + ": " + fillAlpha, x, y, -1);
            }
        } else if (effector.equalsIgnoreCase("Red")) {
            hudGraphics.drawShadowedFont(effector + ": " + borderRed, x, y, -1);
        } else if (effector.equalsIgnoreCase("Green")) {
            hudGraphics.drawShadowedFont(effector + ": " + borderGreen, x, y, -1);
        } else if (effector.equalsIgnoreCase("Blue")) {
            hudGraphics.drawShadowedFont(effector + ": " + borderBlue, x, y, -1);
        } else if (effector.equalsIgnoreCase("Alpha")) {
            hudGraphics.drawShadowedFont(effector + ": " + borderAlpha, x, y, -1);
        }
    }

    private void renderKnob(int x, int y, String color) {
        if (color.equalsIgnoreCase("red")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("green")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("blue")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("alpha")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
        }
    }
}
