package com.j99thoms.uhcessentials.forge;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.api.HUDGraphics;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class ForgeHUDGraphics implements HUDGraphics {

    private final FontRenderer fontRenderer;
    private final Minecraft mc;

    public ForgeHUDGraphics(FontRenderer fontRenderer, Minecraft mc) {
        this.fontRenderer = fontRenderer;
        this.mc = mc;
    }

    @Override
    public int getStringWidth(String text) {
        return fontRenderer.getStringWidth(text);
    }

    @Override
    public void drawFont(String text, int x, int y, int color) {
        fontRenderer.drawString(text, x, y, color);
    }

    @Override
    public void drawShadowedFont(String text, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    @Override
    public void drawItemSprite(int xPos, int yPos, String resourceLocation) {
        Item item = (Item) Item.itemRegistry.getObject(new ResourceLocation(resourceLocation));
        GlStateManager.depthMask(false);
        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        mc.getRenderItem().renderItemIntoGUI(new ItemStack(item), xPos, yPos);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.popMatrix();
    }

    /**
     * Draws a filled rectangle. Colors and alpha range from 0 to 255.
     */
    @Override
    public void drawHUDRect(double x1, double y1, double width, double height,
            double red, double green, double blue, double alpha) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);

        red   = (red   > 255 ? 255 : red)   / 255;
        green = (green > 255 ? 255 : green) / 255;
        blue  = (blue  > 255 ? 255 : blue)  / 255;
        alpha = (alpha > 255 ? 255 : alpha) / 255;

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(1, 0);
        GlStateManager.color((float) red, (float) green, (float) blue, (float) alpha);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x1,         y1);
        GL11.glVertex2d(x1,         y1 + height);
        GL11.glVertex2d(x1 + width, y1 + height);
        GL11.glVertex2d(x1 + width, y1);
        GL11.glEnd();

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    /**
     * Draws a filled rectangle with a border. Colors and alpha range from 0 to 255.
     */
    @Override
    public void drawHUDRectWithBorder(double x1, double y1, double width, double height,
            double red, double green, double blue, double alpha,
            double red2, double green2, double blue2, double alpha2,
            double thickness, boolean left, boolean right, boolean top, boolean bottom) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);

        red    = (red    > 255 ? 255 : red)    / 255;
        green  = (green  > 255 ? 255 : green)  / 255;
        blue   = (blue   > 255 ? 255 : blue)   / 255;
        alpha  = (alpha  > 255 ? 255 : alpha)  / 255;
        red2   = (red2   > 255 ? 255 : red2)   / 255;
        green2 = (green2 > 255 ? 255 : green2) / 255;
        blue2  = (blue2  > 255 ? 255 : blue2)  / 255;
        alpha2 = (alpha2 > 255 ? 255 : alpha2) / 255;

        // Draw fill
        GlStateManager.color((float) red, (float) green, (float) blue, (float) alpha);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2d(x1,         y1);
        GL11.glVertex2d(x1,         y1 + height);
        GL11.glVertex2d(x1 + width, y1 + height);
        GL11.glVertex2d(x1 + width, y1);
        GL11.glEnd();

        GlStateManager.color((float) red2, (float) green2, (float) blue2, (float) alpha2);

        if (left || right || bottom || top) {
            // Draw left border
            if (left && !bottom) {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1 - thickness, y1 - thickness);
                GL11.glVertex2d(x1 - thickness, y1 + height);
                GL11.glVertex2d(x1,             y1 + height);
                GL11.glVertex2d(x1,             y1 - thickness);
                GL11.glEnd();
            } else if (left) {
                GlStateManager.color((float) red2, (float) green2, (float) blue2, (float) alpha2);
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1 - thickness, y1 - thickness);
                GL11.glVertex2d(x1 - thickness, y1 + height + thickness);
                GL11.glVertex2d(x1,             y1 + height + thickness);
                GL11.glVertex2d(x1,             y1 - thickness);
                GL11.glEnd();
            }

            // Draw right border
            if (right && !bottom) {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1 + width,             y1 - thickness);
                GL11.glVertex2d(x1 + width,             y1 + height);
                GL11.glVertex2d(x1 + width + thickness, y1 + height);
                GL11.glVertex2d(x1 + width + thickness, y1 - thickness);
                GL11.glEnd();
            } else if (right) {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1 + width,             y1 - thickness);
                GL11.glVertex2d(x1 + width,             y1 + height + thickness);
                GL11.glVertex2d(x1 + width + thickness, y1 + height + thickness);
                GL11.glVertex2d(x1 + width + thickness, y1 - thickness);
                GL11.glEnd();
            }

            // Draw top border
            if (!bottom) {
                GlStateManager.color((float) red2, (float) green2, (float) blue2, (float) (alpha2 - 0.2f));
            }
            if (bottom) {
                GlStateManager.color((float) red2, (float) green2, (float) blue2, (float) alpha2);
            }
            if (top) {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1,         y1 - thickness);
                GL11.glVertex2d(x1,         y1);
                GL11.glVertex2d(x1 + width, y1);
                GL11.glVertex2d(x1 + width, y1 - thickness);
                GL11.glEnd();
            }

            // Draw bottom border
            if (bottom) {
                GL11.glBegin(GL11.GL_QUADS);
                GL11.glVertex2d(x1,         y1 + height);
                GL11.glVertex2d(x1,         y1 + height + thickness);
                GL11.glVertex2d(x1 + width, y1 + height + thickness);
                GL11.glVertex2d(x1 + width, y1 + height);
                GL11.glEnd();
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.enableTexture2D();
    }

    @Override
    public void drawHUDRectWithBorder(double x1, double y1, double width, double height,
            double red, double green, double blue, double alpha,
            double red2, double green2, double blue2, double alpha2, double thickness) {
        drawHUDRectWithBorder(x1, y1, width, height,
                red, green, blue, alpha, red2, green2, blue2, alpha2,
                thickness, true, true, true, true);
    }

    @Override
    public void drawHUDRectBorder(double x1, double y1, double width, double height,
            double red, double green, double blue, double alpha, double thickness) {
        drawHUDRectWithBorder(x1, y1, width, height,
                0, 0, 0, 0, red, green, blue, alpha, thickness);
    }
}
