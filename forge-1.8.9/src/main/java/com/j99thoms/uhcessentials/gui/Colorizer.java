package com.j99thoms.uhcessentials.gui;

import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.windows.ThemedWindow;

public class Colorizer {

    private static final int PREVIEW_X_OFFSET = 150;
    private static final int PREVIEW_Y_OFFSET = 25;
    private static final int SLIDER_SPACING   = 32;
    private static final int KNOB_WIDTH       = 2;
    private static final int KNOB_HEIGHT      = 7;
    private static final int KNOB_HIT_WIDTH   = 5;
    private static final int BUTTON_Y_OFFSET  = 60;

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
            int x = guiContext.getMouseX();
            int y = guiContext.getMouseY();
            int dx = x - lastX;
            lastX = x;

            int previewX = guiContext.getScreenWidth() / 2 + PREVIEW_X_OFFSET;
            int previewY = guiContext.getScreenHeight() / 2 - PREVIEW_Y_OFFSET;

            int r = border ? borderRed   : fillRed;
            int g = border ? borderGreen : fillGreen;
            int b = border ? borderBlue  : fillBlue;
            int a = border ? borderAlpha : fillAlpha;

            if ((y < redKnobY + KNOB_HEIGHT && y >= redKnobY && x < redKnobX + KNOB_HIT_WIDTH && x >= redKnobX) || grabbedRed) {
                grabbedRed = true;
                setActiveRGBA(clamp(r + dx), g, b, a);
            } else if ((y < greenKnobY + KNOB_HEIGHT && y >= greenKnobY && x < greenKnobX + KNOB_HIT_WIDTH && x >= greenKnobX) || grabbedGreen) {
                grabbedGreen = true;
                setActiveRGBA(r, clamp(g + dx), b, a);
            } else if ((y < blueKnobY + KNOB_HEIGHT && y >= blueKnobY && x < blueKnobX + KNOB_HIT_WIDTH && x >= blueKnobX) || grabbedBlue) {
                grabbedBlue = true;
                setActiveRGBA(r, g, clamp(b + dx), a);
            } else if ((y < alphaKnobY + KNOB_HEIGHT && y >= alphaKnobY && x < alphaKnobX + KNOB_HIT_WIDTH && x >= alphaKnobX) || grabbedAlpha) {
                grabbedAlpha = true;
                setActiveRGBA(r, g, b, clamp(a + dx));
            } else if (x <= previewX + 12 && x >= previewX
                    && y <= previewY + BUTTON_Y_OFFSET + 9 && y >= previewY + BUTTON_Y_OFFSET) {
                border = false;
            } else if (x <= previewX + 33 && x >= previewX
                    && y <= previewY + BUTTON_Y_OFFSET + 19 && y >= previewY + BUTTON_Y_OFFSET + 10) {
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
        int previewX = guiContext.getScreenWidth() / 2 + PREVIEW_X_OFFSET;
        int previewY = guiContext.getScreenHeight() / 2 - PREVIEW_Y_OFFSET;
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

        redKnobY   = sliderMidY - SLIDER_SPACING;
        greenKnobY = sliderMidY;
        blueKnobY  = sliderMidY + SLIDER_SPACING;
        alphaKnobY = sliderMidY + 2 * SLIDER_SPACING;

        renderSlider(sliderBaseX, redKnobY,   "Red",   activeRed);
        renderKnob(redKnobX, redKnobY, "red");
        renderSlider(sliderBaseX, greenKnobY, "Green", activeGreen);
        renderKnob(greenKnobX, greenKnobY, "green");
        renderSlider(sliderBaseX, blueKnobY,  "Blue",  activeBlue);
        renderKnob(blueKnobX, blueKnobY, "blue");
        renderSlider(sliderBaseX, alphaKnobY, "Alpha", activeAlpha);
        renderKnob(alphaKnobX, alphaKnobY, "alpha");

        hudGraphics.drawHUDRectWithBorder(previewX, previewY, 50, 50,
                fillRed, fillGreen, fillBlue, fillAlpha,
                borderRed, borderGreen, borderBlue, borderAlpha, window.getThickness());
        int px = previewX - 1;
        int py = previewY + BUTTON_Y_OFFSET;
        if (!border) {
            hudGraphics.drawHUDRectWithBorder(px, py, 13, 9, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py + 10, 34, 9, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        } else {
            hudGraphics.drawHUDRectWithBorder(px, py + 10, 34, 9, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py, 13, 9, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        }
        hudGraphics.drawShadowedFont("BG", previewX, py, 0xFFFFFF);
        hudGraphics.drawShadowedFont("Outline", previewX, py + 10, 0xFFFFFF);
    }

    private void renderSlider(int x, int y, String label, int value) {
        hudGraphics.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        hudGraphics.drawShadowedFont(label + ": " + value, x, y - 10, -1);
    }

    private void renderKnob(int x, int y, String color) {
        if (color.equalsIgnoreCase("red")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, KNOB_WIDTH, KNOB_HEIGHT, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("green")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, KNOB_WIDTH, KNOB_HEIGHT, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("blue")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, KNOB_WIDTH, KNOB_HEIGHT, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("alpha")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, KNOB_WIDTH, KNOB_HEIGHT, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
        }
    }
}
