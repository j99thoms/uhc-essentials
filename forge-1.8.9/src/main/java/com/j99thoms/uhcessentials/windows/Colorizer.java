package com.j99thoms.uhcessentials.windows;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Mouse;

import com.j99thoms.uhcessentials.HUDGraphics;

public class Colorizer extends GuiScreen {

    private final HUDGraphics hudGraphics;
    private final Colorizable window;
    private final Minecraft mc;

    private int fillRed;
    private int fillGreen;
    private int fillBlue;
    private int fillAlpha;
    private int borderRed;
    private int borderGreen;
    private int borderBlue;
    private int borderAlpha;
    private float thickness;

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
    private int thicknessKnobX;
    private int thicknessKnobY;

    private boolean grabbedRed = false;
    private boolean grabbedGreen = false;
    private boolean grabbedBlue = false;
    private boolean grabbedAlpha = false;
    private boolean grabbedThickness = false;
    private boolean border = false;
    private int cooldown = 0;
    private boolean isCooldown = false;

    public Colorizer(HUDGraphics hudGraphics, Colorizable window, Minecraft mc) {
        this.hudGraphics = hudGraphics;
        this.window = window;
        this.mc = mc;
        getInts();
        this.thickness = (float) window.getThickness();
        mc.displayGuiScreen(this);
    }

    public void update() {
        getInts();
        mouse();
        render();
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
        thickness = (float) window.getThickness();
    }

    private void mouse() {
        if (isCooldown) {
            cooldown++;
        }
        if (cooldown % 2 == 0 && isCooldown) {
            isCooldown = false;
        }
        if (Mouse.isButtonDown(0)) {
            ScaledResolution scaledRes = new ScaledResolution(mc);
            x = Mouse.getEventX() * this.width / mc.displayWidth;
            y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
            dx = x - lastX;
            lastX = x;
            if (!border) {
                fillRed = fillRed <= 0 ? 0 : fillRed;
                fillRed = fillRed > 255 ? 255 : fillRed;
                fillGreen = fillGreen <= 0 ? 0 : fillGreen;
                fillGreen = fillGreen > 255 ? 255 : fillGreen;
                fillBlue = fillBlue <= 0 ? 0 : fillBlue;
                fillBlue = fillBlue > 255 ? 255 : fillBlue;
                fillAlpha = fillAlpha < 0 ? 0 : fillAlpha;
                fillAlpha = fillAlpha > 255 ? 255 : fillAlpha;
                if ((y < redKnobY + 7 && y >= redKnobY && x < redKnobX + 5 && x >= redKnobX) || grabbedRed) {
                    grabbedRed = true;
                    window.setRGBA(fillRed + dx, fillGreen, fillBlue, fillAlpha);
                } else if ((y < greenKnobY + 7 && y >= greenKnobY && x < greenKnobX + 5 && x >= greenKnobX) || grabbedGreen) {
                    grabbedGreen = true;
                    window.setRGBA(fillRed, fillGreen + dx, fillBlue, fillAlpha);
                } else if ((y < blueKnobY + 7 && y >= blueKnobY && x < blueKnobX + 5 && x >= blueKnobX) || grabbedBlue) {
                    grabbedBlue = true;
                    window.setRGBA(fillRed, fillGreen, fillBlue + dx, fillAlpha);
                } else if ((y < alphaKnobY + 7 && y >= alphaKnobY && x < alphaKnobX + 5 && x >= alphaKnobX) || grabbedAlpha) {
                    grabbedAlpha = true;
                    window.setRGBA(fillRed, fillGreen, fillBlue, fillAlpha + dx);
                } else if (x <= scaledRes.getScaledWidth() / 2 + 150 + 12 && x >= scaledRes.getScaledWidth() / 2 + 150
                        && y <= scaledRes.getScaledHeight() / 2 - 25 + 60 + 9 && y >= scaledRes.getScaledHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= scaledRes.getScaledWidth() / 2 + 150 + 12 + 21 && x >= scaledRes.getScaledWidth() / 2 + 150
                        && y <= scaledRes.getScaledHeight() / 2 - 25 + 70 + 9 && y >= scaledRes.getScaledHeight() / 2 - 25 + 70) {
                    border = true;
                }
            } else {
                borderRed = borderRed <= 0 ? 0 : borderRed;
                borderRed = borderRed > 255 ? 255 : borderRed;
                borderGreen = borderGreen <= 0 ? 0 : borderGreen;
                borderGreen = borderGreen > 255 ? 255 : borderGreen;
                borderBlue = borderBlue <= 0 ? 0 : borderBlue;
                borderBlue = borderBlue > 255 ? 255 : borderBlue;
                borderAlpha = borderAlpha <= 0 ? 0 : borderAlpha;
                borderAlpha = borderAlpha > 255 ? 255 : borderAlpha;
                thickness = thickness <= 0 ? 0 : thickness;
                thickness = thickness > 255 ? 255 : thickness;
                if ((y < redKnobY + 7 && y >= redKnobY && x < redKnobX + 5 && x >= redKnobX) || grabbedRed) {
                    grabbedRed = true;
                    window.setBorderRGB(borderRed + dx, borderGreen, borderBlue, borderAlpha);
                } else if ((y < greenKnobY + 7 && y >= greenKnobY && x < greenKnobX + 5 && x >= greenKnobX) || grabbedGreen) {
                    grabbedGreen = true;
                    window.setBorderRGB(borderRed, borderGreen + dx, borderBlue, borderAlpha);
                } else if ((y < blueKnobY + 7 && y >= blueKnobY && x < blueKnobX + 5 && x >= blueKnobX) || grabbedBlue) {
                    grabbedBlue = true;
                    window.setBorderRGB(borderRed, borderGreen, borderBlue + dx, borderAlpha);
                } else if ((y < alphaKnobY + 7 && y >= alphaKnobY && x < alphaKnobX + 5 && x >= alphaKnobX) || grabbedAlpha) {
                    grabbedAlpha = true;
                    window.setBorderRGB(borderRed, borderGreen, borderBlue, borderAlpha + dx);
                } else if ((y < thicknessKnobY + 7 && y >= thicknessKnobY && x < thicknessKnobX + 5 && x >= thicknessKnobX) || grabbedThickness) {
                    window.setThickness(thickness / 255.0f + (float) (dx / 255));
                    grabbedThickness = true;
                } else if (x <= scaledRes.getScaledWidth() / 2 + 150 + 12 && x >= scaledRes.getScaledWidth() / 2 + 150
                        && y <= scaledRes.getScaledHeight() / 2 - 25 + 60 + 9 && y >= scaledRes.getScaledHeight() / 2 - 25 + 60) {
                    border = false;
                } else if (x <= scaledRes.getScaledWidth() / 2 + 150 + 12 + 21 && x >= scaledRes.getScaledWidth() / 2 + 150
                        && y <= scaledRes.getScaledHeight() / 2 - 25 + 70 + 9 && y >= scaledRes.getScaledHeight() / 2 - 25 + 70) {
                    border = true;
                }
            }
            window.setThickness(0.5f);
            window.save();
        } else {
            cooldown = 0;
            grabbedRed = false;
            grabbedGreen = false;
            grabbedBlue = false;
            grabbedAlpha = false;
            grabbedThickness = false;
            lastX = Mouse.getEventX() * this.width / mc.displayWidth;
        }
    }

    private void render() {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);
        ScaledResolution scaledRes = new ScaledResolution(mc);
        if (!border) {
            redKnobX = scaledRes.getScaledWidth() / 2 - 127 + fillRed;
            redKnobY = scaledRes.getScaledHeight() / 2 - 2 - 32;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 - 32, "Red");
            renderKnob(redKnobX, redKnobY, "red");
            greenKnobX = scaledRes.getScaledWidth() / 2 - 127 + fillGreen;
            greenKnobY = scaledRes.getScaledHeight() / 2 - 2;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2, "Green");
            renderKnob(greenKnobX, greenKnobY, "green");
            blueKnobX = scaledRes.getScaledWidth() / 2 - 127 + fillBlue;
            blueKnobY = scaledRes.getScaledHeight() / 2 - 2 + 32;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 + 32, "Blue");
            renderKnob(blueKnobX, blueKnobY, "blue");
            alphaKnobX = scaledRes.getScaledWidth() / 2 - 127 + fillAlpha;
            alphaKnobY = scaledRes.getScaledHeight() / 2 - 2 + 64;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(alphaKnobX, alphaKnobY, "alpha");
        } else {
            redKnobX = scaledRes.getScaledWidth() / 2 - 127 + borderRed;
            redKnobY = scaledRes.getScaledHeight() / 2 - 2 - 32;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 - 32, "Red");
            renderKnob(redKnobX, redKnobY, "red");
            greenKnobX = scaledRes.getScaledWidth() / 2 - 127 + borderGreen;
            greenKnobY = scaledRes.getScaledHeight() / 2 - 2;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2, "Green");
            renderKnob(greenKnobX, greenKnobY, "green");
            blueKnobX = scaledRes.getScaledWidth() / 2 - 127 + borderBlue;
            blueKnobY = scaledRes.getScaledHeight() / 2 - 2 + 32;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 + 32, "Blue");
            renderKnob(blueKnobX, blueKnobY, "blue");
            alphaKnobX = scaledRes.getScaledWidth() / 2 - 127 + borderAlpha;
            alphaKnobY = scaledRes.getScaledHeight() / 2 - 2 + 64;
            renderSlider(scaledRes.getScaledWidth() / 2 - 127, scaledRes.getScaledHeight() / 2 - 2 + 64, "Alpha");
            renderKnob(alphaKnobX, alphaKnobY, "alpha");
        }
        hudGraphics.drawHUDRectWithBorder(scaledRes.getScaledWidth() / 2 + 150, scaledRes.getScaledHeight() / 2 - 25, 50, 50,
                fillRed, fillGreen, fillBlue, fillAlpha,
                borderRed, borderGreen, borderBlue, borderAlpha, window.getThickness());
        int px = scaledRes.getScaledWidth() / 2 + 150 - 1;
        int py = scaledRes.getScaledHeight() / 2 - 25 + 60;
        int pwidth = 13;
        int pheight = 9;
        if (!border) {
            hudGraphics.drawHUDRectWithBorder(px, py, pwidth, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        } else {
            hudGraphics.drawHUDRectWithBorder(px, py + 10, pwidth + 21, pheight, 120, 120, 120, 150, 0, 0, 0, 255, 0.5);
            hudGraphics.drawHUDRectWithBorder(px, py, pwidth, pheight, 69, 69, 69, 150, 0, 0, 0, 255, 0.2f);
        }
        hudGraphics.drawShadowedFont("BG", scaledRes.getScaledWidth() / 2 + 150, scaledRes.getScaledHeight() / 2 - 25 + 60, 0xFFFFFF);
        hudGraphics.drawShadowedFont("Outline", scaledRes.getScaledWidth() / 2 + 150, scaledRes.getScaledHeight() / 2 - 25 + 70, 0xFFFFFF);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    private void renderSlider(int x, int y, String effector) {
        if (fillRed > 255 || fillRed < 0 || fillGreen > 255 || fillGreen < 0
                || fillBlue > 255 || fillBlue < 0 || fillAlpha > 255 || fillAlpha < 0
                || borderRed > 255 || borderRed < 0 || borderGreen > 255 || borderGreen < 0
                || borderBlue > 255 || borderBlue < 0 || borderAlpha > 255 || borderAlpha < 0) {
            window.setRGBA(Math.abs(fillRed <= 255 ? fillRed : 255), Math.abs(fillGreen <= 255 ? fillGreen : 255),
                    Math.abs(fillBlue <= 255 ? fillBlue : 255), Math.abs(fillAlpha <= 255 ? fillAlpha : 255));
            window.setBorderRGB(Math.abs(borderRed <= 255 ? borderRed : 255), Math.abs(borderGreen <= 255 ? borderGreen : 255),
                    Math.abs(borderBlue <= 255 ? borderBlue : 255), Math.abs(borderAlpha <= 255 ? borderAlpha : 255));
            getInts();
        }
        hudGraphics.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, 0.5);
        GlStateManager.enableTexture2D();
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
        } else if (effector.equalsIgnoreCase("Thickness")) {
            hudGraphics.drawShadowedFont(effector + ": " + thickness / 255.0f, x, y, -1);
        }
    }

    private void renderKnob(int x, int y, String color) {
        if (!border) {
            if (color.equalsIgnoreCase("red")) {
                hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("green")) {
                hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("blue")) {
                hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
            } else if (color.equalsIgnoreCase("alpha")) {
                hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
            }
        } else if (color.equalsIgnoreCase("red")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("green")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("blue")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("alpha")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, 0.3f);
        } else if (color.equalsIgnoreCase("thickness")) {
            hudGraphics.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 0, 100, 255, 255, 255, 255, 0.3f);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
