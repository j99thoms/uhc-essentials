package com.j99thoms.uhcessentials.gui;

import com.j99thoms.uhcessentials.api.HUDGraphics;

class Button {

    private static final int FONT_HEIGHT = 8;

    private final HUDGraphics hudGraphics;

    private String label;       // null = unlabeled
    private int labelPadding;

    int x, y, width, height;
    int hitPaddingX = 0;
    int hitPaddingY = 0;

    public Button(HUDGraphics hudGraphics, int x, int y, int width, int height) {
        this(hudGraphics, x, y, width, height, null, 0);
    }
    
    private Button(HUDGraphics hudGraphics, int x, int y, int width, int height, String label, int labelPadding) {
        this.hudGraphics = hudGraphics;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.labelPadding = labelPadding;
    }

    // Label-derived factory; position starts at (0,0)
    public static Button fromLabel(HUDGraphics hudGraphics, String label, int padding, int height) {
        int width = hudGraphics.getStringWidth(label) + padding;
        return new Button(hudGraphics, 0, 0, width, height, label, padding);
    }

    public void setLabel(String label) {
        setLabel(label, true);
    }

    public void setLabel(String label, boolean recalcWidth) {
        this.label = label;
        if (recalcWidth)
            this.width = hudGraphics.getStringWidth(label) + labelPadding;
    }

    public boolean contains(int x, int y) {
        return x >= this.x - hitPaddingX && x <= this.x + width  + hitPaddingX
            && y >= this.y - hitPaddingY && y <= this.y + height + hitPaddingY;
    }

    // render() with default white text
    public void render(int fillR, int fillG, int fillB, int fillA,
            int borderR, int borderG, int borderB, int borderA,
            double thickness) {
        render(fillR, fillG, fillB, fillA, borderR, borderG, borderB, borderA, thickness, -1);
    }

    public void render(int fillR, int fillG, int fillB, int fillA,
            int borderR, int borderG, int borderB, int borderA,
            double thickness, int textColor) {
        hudGraphics.drawHUDRectWithBorder(x, y, width, height,
            fillR, fillG, fillB, fillA, borderR, borderG, borderB, borderA, thickness);
        if (label != null) {
            int textX = x + labelPadding / 2;
            int textY = y + (height - FONT_HEIGHT) / 2;
            hudGraphics.drawShadowedFont(label, textX, textY, textColor);
        }
    }
}
