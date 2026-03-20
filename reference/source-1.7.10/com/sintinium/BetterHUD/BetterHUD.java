package com.sintinium.BetterHUD;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;

import org.lwjgl.opengl.GL12;

import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.WindowManager;

public class BetterHUD extends GuiScreen {
	public boolean shouldRender = false;
	public boolean move = false;
	private FontRenderer FR;
	private WindowManager WM;
	private RenderItem RI;
	private Minecraft mc;

	private String currentVersion = "1.1.2";
	private String update;
	private ArrayList updateCheck = new ArrayList();
	private static boolean checkedUpdate = false;
	private VersionChecker vc;
	private boolean newVersion = false;
	private int timer;

	public static boolean resetDefaults = false;

	public BetterHUD(FontRenderer FR, Minecraft mc) {
		this.FR = FR;
		WM = new WindowManager(this, FR, mc);
		RI = new RenderItem();
		this.mc = mc;
		vc = new VersionChecker();
		this.updateCheck = vc.getCurrentVersion();
		try {
			this.update = (String) this.updateCheck.get(0);
			if (!this.update.equals(currentVersion) && checkedUpdate == false) {
				newVersion = true;
			}
		} catch (Exception e) {
		}
	}

	public void setRenderState(boolean renderState) {
		this.shouldRender = renderState;
	}

	public void update() {
		if (this.shouldRender) {
			render();
			WM.update();
		}
		// newVersion = true;
		if (newVersion) {
			if (checkedUpdate == false) {
				checkedUpdate = true;
			}
			this.mc.thePlayer.addChatComponentMessage(new ChatComponentTranslation("UHC Essentials version " + this.update + " is ready for download"));
			for (int i = 1; i < updateCheck.size(); i++) {
				this.mc.thePlayer.addChatComponentMessage(new ChatComponentTranslation((String) updateCheck.get(i)));
			}
			newVersion = false;
		}
	}

	public void render() {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		WM.render();
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
	}

	public void drawItemSprite(int xPos, int yPos, int itemID, DefaultWindow DW) {
		glEnable(32826);
		glPushMatrix();

		RenderHelper.enableGUIStandardItemLighting();

		ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

		RI.renderItemIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), new ItemStack(Item.getItemById(itemID)), xPos, yPos);

		RenderHelper.disableStandardItemLighting();
		glDisable(GL12.GL_RESCALE_NORMAL);
		glEnable(32826);
		glPopMatrix();
	}

	/**
	 * Draws rectangle to screen colors and alpha range from 0 to 255
	 */
	public void drawHUDRect(double x1, double y1, double width, double height, double red, double green, double blue, double alpha) {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		red = (Double) (red > 255 ? 255 : red) / 255;
		green = (Double) (green > 255 ? 255 : green) / 255;
		blue = (Double) (blue > 255 ? 255 : blue) / 255;
		alpha = (Double) (alpha > 255 ? 255 : alpha) / 255;
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ZERO);
		glColor4d(red, green, blue, alpha);
		glBegin(GL_QUADS);
		glVertex2d(x1, y1);
		glVertex2d(x1, y1 + height);
		glVertex2d(x1 + width, y1 + height);
		glVertex2d(x1 + width, y1);
		glEnd();

		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
	}

	/**
	 * Draws rectangle with border to screen
	 */
	public void drawHUDRectWithBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double red2, double green2, double blue2, double alpha2, double thickness, boolean left, boolean right, boolean top, boolean bottom) {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		red = (Double) (red > 255 ? 255 : red) / 255;
		green = (Double) (green > 255 ? 255 : green) / 255;
		blue = (Double) (blue > 255 ? 255 : blue) / 255;
		alpha = (Double) (alpha > 255 ? 255 : alpha) / 255;
		red2 = (Double) (red2 > 255 ? 255 : red2) / 255;
		green2 = (Double) (green2 > 255 ? 255 : green2) / 255;
		blue2 = (Double) (blue2 > 255 ? 255 : blue2) / 255;
		alpha2 = (Double) (alpha2 > 255 ? 255 : alpha2) / 255;
		// Draw Rect
		glColor4d(red, green, blue, alpha);
		glBegin(GL_QUADS);
		glVertex2d(x1, y1);
		glVertex2d(x1, y1 + height);
		glVertex2d(x1 + width, y1 + height);
		glVertex2d(x1 + width, y1);
		glEnd();
		glColor4d(red2, green2, blue2, alpha2);

		// Draw Left border
		if (left || right || bottom || top) {
			if (left && !bottom) {
				glBegin(GL_QUADS);
				glVertex2d(x1 - thickness, y1 - thickness);
				glVertex2d(x1 - thickness, y1 + height);
				glVertex2d(x1, y1 + height);
				glVertex2d(x1, y1 - thickness);
				glEnd();
			} else if (left) {
				glColor4d(red2, green2, blue2, alpha2);
				glBegin(GL_QUADS);
				glVertex2d(x1 - thickness, y1 - thickness);
				glVertex2d(x1 - thickness, y1 + height + thickness);
				glVertex2d(x1, y1 + height + thickness);
				glVertex2d(x1, y1 - thickness);
				glEnd();
			}

			// Draw Right border
			if (right && !bottom) {
				glBegin(GL_QUADS);
				glVertex2d(x1 + width, y1 - thickness);
				glVertex2d(x1 + width, y1 + height);
				glVertex2d(x1 + width + thickness, y1 + height);
				glVertex2d(x1 + width + thickness, y1 - thickness);
				glEnd();
			} else if (right) {
				glBegin(GL_QUADS);
				glVertex2d(x1 + width, y1 - thickness);
				glVertex2d(x1 + width, y1 + height + thickness);
				glVertex2d(x1 + width + thickness, y1 + height + thickness);
				glVertex2d(x1 + width + thickness, y1 - thickness);
				glEnd();
			}

			// Draw Top border
			if (!bottom) {
				glColor4d(red2, green2, blue2, alpha2 - .2f);
			}

			if (bottom) {
				glColor4d(red2, green2, blue2, alpha2);
			}

			if (top) {
				glBegin(GL_QUADS);
				glVertex2d(x1, y1 - thickness);
				glVertex2d(x1, y1);
				glVertex2d(x1 + width, y1);
				glVertex2d(x1 + width, y1 - thickness);
				glEnd();
			}

			// Draw Bottom border
			if (bottom) {
				glBegin(GL_QUADS);
				glVertex2d(x1, y1 + height);
				glVertex2d(x1, y1 + height + thickness);
				glVertex2d(x1 + width, y1 + height + thickness);
				glVertex2d(x1 + width, y1 + height);
				glEnd();
			}
		}
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
	}

	public void drawHUDRectWithBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double red2, double green2, double blue2, double alpha2, double thickness) {
		drawHUDRectWithBorder(x1, y1, width, height, red, green, blue, alpha, red2, green2, blue2, alpha2, thickness, true, true, true, true);
	}

	public void drawHUDRectBorder(double x1, double y1, double width, double height, double red, double green, double blue, double alpha, double thickness) {
		drawHUDRectWithBorder(x1, y1, width, height, 0, 0, 0, 0, red, green, blue, alpha, thickness);
	}
}
