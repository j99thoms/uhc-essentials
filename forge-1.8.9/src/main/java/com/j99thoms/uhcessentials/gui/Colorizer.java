package com.j99thoms.uhcessentials.gui;

import java.util.EnumMap;

import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.windows.Themeable;

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
    private static final int KNOB_HIT_PADDING_X      = 2;
    private static final int BUTTON_PADDING          = 2;
    private static final int BUTTON_HEIGHT           = 9;
    private static final int FILL_BUTTON_Y_OFFSET    = PREVIEW_WINDOW_SIZE + 10;
    private static final int BORDER_BUTTON_Y_OFFSET  = PREVIEW_WINDOW_SIZE + 20;

    private final HUDGraphics hudGraphics;
    private final Themeable window;
    private final GuiContext guiContext;

    private int lastX;
    private boolean mouseHeldDown = false;
    private boolean border = false;
    private Button fillButton, borderButton;
    private Channel grabbedKnob = null;
    private EnumMap<Channel, Button> knobButtons = new EnumMap<>(Channel.class);

    public Colorizer(HUDGraphics hudGraphics, Themeable window, GuiContext guiContext) {
        this.hudGraphics = hudGraphics;
        this.window = window;
        this.guiContext = guiContext;

        lastX = guiContext.getMouseX();

        fillButton = Button.fromLabel(hudGraphics, "BG", BUTTON_PADDING, BUTTON_HEIGHT);
        borderButton = Button.fromLabel(hudGraphics, "Outline", BUTTON_PADDING, BUTTON_HEIGHT);
    }

    public void update() {
        updateButtonPositions();
        updateKnobPositions();
        mouse();
        render();
    }

    private void updateButtonPositions() {
        int previewWindowX = getPreviewWindowX();
        int previewWindowY = getPreviewWindowY();

        fillButton.x   = previewWindowX - 1;
        borderButton.x = previewWindowX - 1;

        fillButton.y   = previewWindowY + FILL_BUTTON_Y_OFFSET;
        borderButton.y = previewWindowY + BORDER_BUTTON_Y_OFFSET;
    }

    private int getPreviewWindowX() {
        return guiContext.getScreenWidth() / 2 + PREVIEW_WINDOW_X_OFFSET;
    }

    private int getPreviewWindowY() {
        return guiContext.getScreenHeight() / 2 + PREVIEW_WINDOW_Y_OFFSET;
    }

    private void updateKnobPositions() {
        for (Channel channel : Channel.values()) {
            Button knob = knobButtons.computeIfAbsent(channel, c -> getKnob(c));
            knob.x = getKnobX(channel);
            knob.y = getKnobY(channel);
        }
    }

    private Button getKnob(Channel channel) {
        Button knob = new Button(hudGraphics, getKnobX(channel), getKnobY(channel), KNOB_WIDTH, KNOB_HEIGHT);
        knob.hitPaddingX = KNOB_HIT_PADDING_X;
        return knob;
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

    private void mouse() {
        int x = guiContext.getMouseX();
        int y = guiContext.getMouseY();

        if (!guiContext.isMouseButtonDown(0)) {
            mouseHeldDown = false;
            grabbedKnob = null;
            lastX = x;
            return;
        } else if (mouseHeldDown && grabbedKnob == null) {
            lastX = x;
            return;
        } else if (!mouseHeldDown) {
            // If mouse was clicked this frame, need to check if a knob or button was clicked:
            for (Channel channel : Channel.values()) {
                if (knobButtons.get(channel).contains(x, y)) {
                    grabbedKnob = channel;
                    break;
                }
            }

            if (fillButton.contains(x, y))
                border = false;
            else if (borderButton.contains(x, y))
                border = true;

            mouseHeldDown = true;
            lastX = x;
            return;
        }
        // Else, the mouse is held down and a knob is grabbed:
        int dx = x - lastX;
        lastX = x;

        int r = getActiveVal(Channel.RED)   + (grabbedKnob == Channel.RED   ? dx : 0);
        int g = getActiveVal(Channel.GREEN) + (grabbedKnob == Channel.GREEN ? dx : 0);
        int b = getActiveVal(Channel.BLUE)  + (grabbedKnob == Channel.BLUE  ? dx : 0);
        int a = getActiveVal(Channel.ALPHA) + (grabbedKnob == Channel.ALPHA ? dx : 0);

        setActiveRGBA(clamp(r), clamp(g), clamp(b), clamp(a));
        window.save();
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
        renderPreviewWindow();
        renderButtons();
        for (Channel channel : Channel.values()) {
            renderSlider(channel);
            renderKnob(channel);
        }
    }

    private void renderPreviewWindow() {
        hudGraphics.drawHUDRectWithBorder(getPreviewWindowX(), getPreviewWindowY(), PREVIEW_WINDOW_SIZE, PREVIEW_WINDOW_SIZE,
            window.getR(), window.getG(), window.getB(), window.getA(),
            window.getBorderR(), window.getBorderG(), window.getBorderB(), window.getBorderA(), window.getThickness());
    }

    private void renderButtons() {
        int fillButtonShade = !border ? 120 : 69;
        int borderButtonShade = border ? 120 : 69;

        fillButton.render(fillButtonShade, fillButtonShade, fillButtonShade, 150,
            0,0,0,255, 0.5);
        borderButton.render(borderButtonShade, borderButtonShade, borderButtonShade, 150,
            0,0,0,255, 0.5);
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

        knobButtons.get(channel).render(knobR, knobG, knobB, knobA,
            0,0,0,255, 0.3f);
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
