package com.j99thoms.uhcessentials.gui;

import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.windows.ThemedWindow;

public class Colorizer {

    // GUI layout constants: all are values in pixels
    private static final int PREVIEW_WINDOW_X_OFFSET = 150;
    private static final int PREVIEW_WINDOW_Y_OFFSET = -25;
    private static final int PREVIEW_WINDOW_SIZE     = 50;
    private static final int SLIDER_BASE_X_OFFSET    = -127;
    private static final int SLIDER_BASE_Y_OFFSET    = -34;
    private static final int SLIDER_SPACING          = 32;
    private static final int KNOB_WIDTH              = 2;
    private static final int KNOB_HEIGHT             = 7;
    private static final int KNOB_HIT_WIDTH          = 5;
    private static final int FILL_BUTTON_WIDTH       = 13;
    private static final int BORDER_BUTTON_WIDTH     = 34;
    private static final int BUTTON_HEIGHT           = 9;
    private static final int FILL_BUTTON_Y_OFFSET    = PREVIEW_WINDOW_SIZE + 10;
    private static final int BORDER_BUTTON_Y_OFFSET  = PREVIEW_WINDOW_SIZE + 20;

    private final HUDGraphics hudGraphics;
    private final ThemedWindow window;
    private final GuiContext guiContext;

    private int lastX;
    private boolean mouseHeldDown = false;
    private boolean grabbedRed = false;
    private boolean grabbedGreen = false;
    private boolean grabbedBlue = false;
    private boolean grabbedAlpha = false;
    private boolean border = false;

    public Colorizer(HUDGraphics hudGraphics, ThemedWindow window, GuiContext guiContext) {
        this.hudGraphics = hudGraphics;
        this.window = window;
        this.guiContext = guiContext;
        lastX = guiContext.getMouseX();
    }

    public void update() {
        mouse();
        render();
    }

    private void mouse() {
        int x = guiContext.getMouseX();
        int y = guiContext.getMouseY();

        if (!guiContext.isMouseButtonDown(0)) {
            mouseHeldDown = grabbedRed = grabbedGreen = grabbedBlue = grabbedAlpha = false;
            lastX = x;
            return;
        } else if (mouseHeldDown && !(grabbedRed || grabbedGreen || grabbedBlue || grabbedAlpha)) {
            lastX = x;
            return;
        } else if (!mouseHeldDown) {
            // If mouse was clicked this frame, need to check if a knob or button was clicked:
            if (knobIsClicked(Channel.RED, x, y))
                grabbedRed = true;
            else if (knobIsClicked(Channel.GREEN, x, y))
                grabbedGreen = true;
            else if (knobIsClicked(Channel.BLUE, x, y))
                grabbedBlue = true;
            else if (knobIsClicked(Channel.ALPHA, x, y))
                grabbedAlpha = true;
            else if (fillButtonIsClicked(x, y))
                border = false;
            else if (borderButtonIsClicked(x, y))
                border = true;

            mouseHeldDown = true;
            lastX = x;
            return;
        }
        // Else, the mouse is held down and a knob is grabbed:
        int dx = x - lastX;
        lastX = x;

        int r = getActiveVal(Channel.RED) + (grabbedRed ? dx : 0);
        int g = getActiveVal(Channel.GREEN) + (grabbedGreen ? dx : 0);
        int b = getActiveVal(Channel.BLUE) + (grabbedBlue ? dx : 0);
        int a = getActiveVal(Channel.ALPHA) + (grabbedAlpha ? dx : 0);

        setActiveRGBA(clamp(r), clamp(g), clamp(b), clamp(a));
        window.save();
    }

    private boolean knobIsClicked(Channel channel, int mouseX, int mouseY) {
        int knobX = getKnobX(channel);
        int knobY = getKnobY(channel);
        return mouseX <= knobX + KNOB_HIT_WIDTH && mouseX >= knobX && mouseY < knobY + KNOB_HEIGHT && mouseY >= knobY;
    }

    private boolean fillButtonIsClicked(int mouseX, int mouseY) {
        int buttonX = getPreviewWindowX() - 1;
        int buttonY = getFillButtonY();
        return mouseX <= buttonX + FILL_BUTTON_WIDTH && mouseX >= buttonX && mouseY <= buttonY + BUTTON_HEIGHT && mouseY >= buttonY;
    }

    private boolean borderButtonIsClicked(int mouseX, int mouseY) {
        int buttonX = getPreviewWindowX() - 1;
        int buttonY = getBorderButtonY();
        return mouseX <= buttonX + BORDER_BUTTON_WIDTH && mouseX >= buttonX && mouseY <= buttonY + BUTTON_HEIGHT && mouseY >= buttonY;
    }

    private int getKnobX(Channel channel) {
        return getSliderBaseX() + getActiveVal(channel);
    }

    private int getKnobY(Channel channel) {
        return getSliderBaseY(channel) - 1;
    }

    private int getSliderBaseX() {
        return guiContext.getScreenWidth() / 2 + SLIDER_BASE_X_OFFSET;
    }

    private int getSliderBaseY(Channel channel) {
        return guiContext.getScreenHeight() / 2 + SLIDER_BASE_Y_OFFSET + channel.index * SLIDER_SPACING;
    }

    private int getPreviewWindowX() {
        return guiContext.getScreenWidth() / 2 + PREVIEW_WINDOW_X_OFFSET;
    }

    private int getPreviewWindowY() {
        return guiContext.getScreenHeight() / 2 + PREVIEW_WINDOW_Y_OFFSET;
    }

    private int getFillButtonY() {
        return getPreviewWindowY() + FILL_BUTTON_Y_OFFSET;
    }

    private int getBorderButtonY() {
        return getPreviewWindowY() + BORDER_BUTTON_Y_OFFSET;
    }

    private int getActiveVal(Channel channel) {
        switch (channel) {
            case RED:
                return border ? window.getBorderR() : window.getR();
            case GREEN:
                return border ? window.getBorderG() : window.getG();
            case BLUE:
                return border ? window.getBorderB() : window.getB();
            case ALPHA:
                return border ? window.getBorderA() : window.getA();
            default:
                throw new IllegalStateException("Unknown channel: " + channel);
        }
    }

    private void setActiveRGBA(int r, int g, int b, int a) {
        if (border) window.setBorderRGB(r, g, b, a);
        else window.setRGBA(r, g, b, a);
    }

    private void render() {
        for (Channel channel : Channel.values()) {
            renderSlider(channel);
            renderKnob(channel);
        }
        renderPreviewWindow();
        renderButtons();
    }

    private void renderSlider(Channel channel) {
        int baseX = getSliderBaseX();
        int baseY = getSliderBaseY(channel);
        hudGraphics.drawHUDRectWithBorder(baseX, baseY, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        hudGraphics.drawShadowedFont(channel.toString() + ": " + getActiveVal(channel), baseX, baseY - 10, -1);
    }

    private void renderKnob(Channel channel) {
        int knobR, knobG, knobB, knobA;
        if (channel == Channel.ALPHA) {
            knobR = knobG = knobB = 255;
            knobA = 100;
        } else {
            knobR = channel == Channel.RED ? 255 : 0;
            knobG = channel == Channel.GREEN ? 255 : 0;
            knobB = channel == Channel.BLUE ? 255 : 0;
            knobA = 255;
        }

        hudGraphics.drawHUDRectWithBorder(getKnobX(channel), getKnobY(channel), KNOB_WIDTH, KNOB_HEIGHT,
            knobR, knobG, knobB, knobA,
            0, 0, 0, 255, 0.3f);
    }

    private void renderPreviewWindow() {
        hudGraphics.drawHUDRectWithBorder(getPreviewWindowX(), getPreviewWindowY(), PREVIEW_WINDOW_SIZE, PREVIEW_WINDOW_SIZE,
                window.getR(), window.getG(), window.getB(), window.getA(),
                window.getBorderR(), window.getBorderG(), window.getBorderB(), window.getBorderA(), window.getThickness());
    }

    private void renderButtons() {
        int buttonX = getPreviewWindowX() - 1;
        int fillButtonY = getFillButtonY();
        int borderButtonY = getBorderButtonY();

        int fillButtonShade = !border ? 120 : 69;
        int borderButtonShade = border ? 120 : 69;

        hudGraphics.drawHUDRectWithBorder(buttonX, fillButtonY, FILL_BUTTON_WIDTH, BUTTON_HEIGHT,
            fillButtonShade, fillButtonShade, fillButtonShade, 150,
            0, 0, 0, 255, 0.5);
        hudGraphics.drawHUDRectWithBorder(buttonX, borderButtonY, BORDER_BUTTON_WIDTH, BUTTON_HEIGHT,
            borderButtonShade, borderButtonShade, borderButtonShade, 150,
            0, 0, 0, 255, 0.2f);

        hudGraphics.drawShadowedFont("BG", buttonX + 1, fillButtonY, 0xFFFFFF);
        hudGraphics.drawShadowedFont("Outline", buttonX + 1, borderButtonY, 0xFFFFFF);
    }

    private static int clamp(int value) { return Math.max(0, Math.min(255, value)); }

    private static enum Channel {
        RED(0), GREEN(1), BLUE(2), ALPHA(3);

        final int index;
        Channel(int index) { this.index = index; }

        @Override
        public String toString() {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }
}
