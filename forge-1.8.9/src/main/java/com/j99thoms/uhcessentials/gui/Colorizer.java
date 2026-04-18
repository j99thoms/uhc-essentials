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

    private void setActiveRGBA(int r, int g, int b, int a) {
        if (border) window.setBorderRGB(r, g, b, a);
        else window.setRGBA(r, g, b, a);
    }

    private void mouse() {
        if (guiContext.isMouseButtonDown(0)) {
            x = guiContext.getMouseX();
            y = guiContext.getMouseY();
            dx = x - lastX;
            lastX = x;

            int previewX = guiContext.getScreenWidth() / 2 + 150;
            int previewY = guiContext.getScreenHeight() / 2 - 25;

            int r = border ? borderRed   : fillRed;
            int g = border ? borderGreen : fillGreen;
            int b = border ? borderBlue  : fillBlue;
            int a = border ? borderAlpha : fillAlpha;

            if ((y < redKnobY + 7 && y >= redKnobY && x < redKnobX + 5 && x >= redKnobX) || grabbedRed) {
                grabbedRed = true;
                setActiveRGBA(clamp(r + dx), g, b, a);
            } else if ((y < greenKnobY + 7 && y >= greenKnobY && x < greenKnobX + 5 && x >= greenKnobX) || grabbedGreen) {
                grabbedGreen = true;
                setActiveRGBA(r, clamp(g + dx), b, a);
            } else if ((y < blueKnobY + 7 && y >= blueKnobY && x < blueKnobX + 5 && x >= blueKnobX) || grabbedBlue) {
                grabbedBlue = true;
                setActiveRGBA(r, g, clamp(b + dx), a);
            } else if ((y < alphaKnobY + 7 && y >= alphaKnobY && x < alphaKnobX + 5 && x >= alphaKnobX) || grabbedAlpha) {
                grabbedAlpha = true;
                setActiveRGBA(r, g, b, clamp(a + dx));
            } else if (x <= previewX + 12 && x >= previewX && y <= previewY + 69 && y >= previewY + 60) {
                border = false;
            } else if (x <= previewX + 33 && x >= previewX && y <= previewY + 79 && y >= previewY + 70) {
                border = true;
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
        int sliderBaseX = guiContext.getScreenWidth() / 2 - 127;
        int sliderMidY = guiContext.getScreenHeight() / 2 - 2;

        int activeRed   = border ? borderRed   : fillRed;
        int activeGreen = border ? borderGreen : fillGreen;
        int activeBlue  = border ? borderBlue  : fillBlue;
        int activeAlpha = border ? borderAlpha : fillAlpha;

        redKnobX   = sliderBaseX + activeRed;
        greenKnobX = sliderBaseX + activeGreen;
        blueKnobX  = sliderBaseX + activeBlue;
        alphaKnobX = sliderBaseX + activeAlpha;

        redKnobY   = sliderMidY - 32;
        greenKnobY = sliderMidY;
        blueKnobY  = sliderMidY + 32;
        alphaKnobY = sliderMidY + 64;

        renderSlider(sliderBaseX, redKnobY,   "Red",   activeRed);
        renderKnob(redKnobX, redKnobY, "red");
        renderSlider(sliderBaseX, greenKnobY, "Green", activeGreen);
        renderKnob(greenKnobX, greenKnobY, "green");
        renderSlider(sliderBaseX, blueKnobY,  "Blue",  activeBlue);
        renderKnob(blueKnobX, blueKnobY, "blue");
        renderSlider(sliderBaseX, alphaKnobY, "Alpha", activeAlpha);
        renderKnob(alphaKnobX, alphaKnobY, "alpha");

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

    private void renderSlider(int x, int y, String label, int value) {
        hudGraphics.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        hudGraphics.drawShadowedFont(label + ": " + value, x, y - 10, -1);
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
