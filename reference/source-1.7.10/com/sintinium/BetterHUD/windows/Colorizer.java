package com.sintinium.BetterHUD.windows;

import static org.lwjgl.opengl.GL11.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Mouse;

import com.sintinium.BetterHUD.BetterHUD;

public class Colorizer extends GuiScreen {

	private BetterHUD BH;
	private DefaultWindow DW;
	private Minecraft mc;

	private int r, g, b, a, r1, g1, b1, a1;
	private float thickness;

	private int x, y, dx, dy, lastX, lastY;
	private int rx, ry, gx, gy, bx, by, ax, ay, tx, ty;
	private boolean grabbedr = false;
	private boolean grabbedg = false;
	private boolean grabbedb = false;
	private boolean grabbeda = false;
	private boolean grabbedthickness = false;
	private boolean grabbedthick = false;
	private boolean border = false;
	private int cooldown = 0;
	private boolean isCooldown = false;

	public Colorizer(BetterHUD BH, DefaultWindow DW, Minecraft mc) {
		this.BH = BH;
		this.DW = DW;
		this.mc = mc;
		getInts();
		thickness = (float) (DW.getThickness());
		this.mc.displayGuiScreen(this);
	}

	public void update() {
		getInts();
		mouse();
		render();
	}

	private void getInts() {
		r = DW.getR();
		g = DW.getG();
		b = DW.getB();
		a = DW.getA();
		r1 = DW.getBorderR();
		g1 = DW.getBorderG();
		b1 = DW.getBorderB();
		a1 = DW.getBorderA();
		thickness = (float) (DW.getThickness());
	}

	private void mouse() {
		if (isCooldown)
			cooldown++;
		if (cooldown % 2 == 0 && isCooldown)
			isCooldown = false;
		if (Mouse.isButtonDown(0)) {
			ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

			x = Mouse.getX();
			y = Mouse.getY();
			x = Mouse.getEventX() * this.width / this.mc.displayWidth;
			y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
			dx = x - lastX;
			dy = y - lastY;
			lastX = x;
			lastY = y;

			if (!this.border) {
				r = r <= 0 ? 0 : r;
				r = r > 255 ? 255 : r;

				g = g <= 0 ? 0 : g;
				g = g > 255 ? 255 : g;

				b = b <= 0 ? 0 : b;
				b = b > 255 ? 255 : b;

				a = a < 0 ? 0 : a;
				a = a > 255 ? 255 : a;

				if ((y < ry + 7 && y >= ry && x < rx + 5 && x >= rx) || grabbedr) {
					grabbedr = true;
					DW.setRGBA(r + dx, g, b, a);
				} else if ((y < gy + 7 && y >= gy && x < gx + 5 && x >= gx) || grabbedg) {
					grabbedg = true;
					DW.setRGBA(r, g + dx, b, a);
				} else if ((y < by + 7 && y >= by && x < bx + 5 && x >= bx) || grabbedb) {
					grabbedb = true;
					DW.setRGBA(r, g, b + dx, a);
				} else if ((y < ay + 7 && y >= ay && x < ax + 5 && x >= ax) || grabbeda) {
					grabbeda = true;
					if (!this.border)
						DW.setRGBA(r, g, b, a + dx);
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 60 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 60) {
					this.border = false;
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 + 21 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 70 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 70) {
					this.border = true;
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 + 21 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 80 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 80) {
					if (cooldown == 0) {
						DW.toggle();
						isCooldown = true;
					}
				}
			} else {
				r1 = r1 <= 0 ? 0 : r1;
				r1 = r1 > 255 ? 255 : r1;

				g1 = g1 <= 0 ? 0 : g1;
				g1 = g1 > 255 ? 255 : g1;

				b1 = b1 <= 0 ? 0 : b1;
				b1 = b1 > 255 ? 255 : b1;

				a1 = a1 <= 0 ? 0 : a1;
				a1 = a1 > 255 ? 255 : a1;

				thickness = thickness <= 0 ? 0 : thickness;
				thickness = thickness > 255 ? 255 : thickness;

				if ((y < ry + 7 && y >= ry && x < rx + 5 && x >= rx) || grabbedr) {
					grabbedr = true;
					DW.setBorderRGB(r1 + dx, g1, b1, a1);
				} else if ((y < gy + 7 && y >= gy && x < gx + 5 && x >= gx) || grabbedg) {
					grabbedg = true;
					DW.setBorderRGB(r1, g1 + dx, b1, a1);
				} else if ((y < by + 7 && y >= by && x < bx + 5 && x >= bx) || grabbedb) {
					grabbedb = true;
					DW.setBorderRGB(r1, g1, b1 + dx, a1);
				} else if ((y < ay + 7 && y >= ay && x < ax + 5 && x >= ax) || grabbeda) {
					grabbeda = true;
					DW.setBorderRGB(r1, g1, b1, a1 + dx);
				} else if ((y < ty + 7 && y >= ty && x < tx + 5 && x >= tx) || grabbedthickness) {
					DW.setThickness((float) (thickness / 255) + dx / 255);
					grabbedthickness = true;
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 60 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 60) {
					this.border = false;
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 + 21 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 70 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 70) {
					this.border = true;
				} else if (x <= (var1.getScaledWidth() / 2) + (300 / 2) + 12 + 21 && x >= (var1.getScaledWidth() / 2) + (300 / 2) && y <= (var1.getScaledHeight() / 2) - (50 / 2) + 80 + 9 && y >= (var1.getScaledHeight() / 2) - (50 / 2) + 80) {
					if (cooldown == 0) {
						DW.toggle();
						isCooldown = true;
					}
				}
			}
			DW.setThickness(.5f);
			DW.save();
		} else {
			cooldown = 0;
			grabbedr = false;
			grabbedg = false;
			grabbedb = false;
			grabbeda = false;
			grabbedthickness = false;
			lastX = Mouse.getEventX() * this.width / this.mc.displayWidth;
			lastY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
		}
	}

	private void render() {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);

		if (!this.border) {
			rx = (var1.getScaledWidth() / 2) - (255 / 2) + r;
			ry = (var1.getScaledHeight() / 2) - (5 / 2) - 32;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) - 32, "Red");
			renderKnob(rx, ry, "red");

			gx = (var1.getScaledWidth() / 2) - (255 / 2) + g;
			gy = (var1.getScaledHeight() / 2) - (5 / 2);
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2), "Green");
			renderKnob(gx, gy, "green");

			bx = (var1.getScaledWidth() / 2) - (255 / 2) + b;
			by = (var1.getScaledHeight() / 2) - (5 / 2) + 32;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) + 32, "Blue");
			renderKnob(bx, by, "blue");

			ax = (var1.getScaledWidth() / 2) - (255 / 2) + a;
			ay = (var1.getScaledHeight() / 2) - (5 / 2) + 64;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) + 64, "Alpha");
			renderKnob(ax, ay, "alpha");
		} else {
			rx = (var1.getScaledWidth() / 2) - (255 / 2) + r1;
			ry = (var1.getScaledHeight() / 2) - (5 / 2) - 32;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) - 32, "Red");
			renderKnob(rx, ry, "red");

			gx = (var1.getScaledWidth() / 2) - (255 / 2) + g1;
			gy = (var1.getScaledHeight() / 2) - (5 / 2);
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2), "Green");
			renderKnob(gx, gy, "green");

			bx = (var1.getScaledWidth() / 2) - (255 / 2) + b1;
			by = (var1.getScaledHeight() / 2) - (5 / 2) + 32;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) + 32, "Blue");
			renderKnob(bx, by, "blue");

			ax = (var1.getScaledWidth() / 2) - (255 / 2) + a1;
			ay = (var1.getScaledHeight() / 2) - (5 / 2) + 64;
			this.renderSlider((var1.getScaledWidth() / 2) - (255 / 2), (var1.getScaledHeight() / 2) - (5 / 2) + 64, "Alpha");
			renderKnob(ax, ay, "alpha");
		}
		BH.drawHUDRectWithBorder((var1.getScaledWidth() / 2) + (300 / 2), (var1.getScaledHeight() / 2) - (50 / 2), 50, 50, r, g, b, a, r1, g1, b1, a1, DW.getThickness());

		int x = (var1.getScaledWidth() / 2) + (300 / 2) - 1;
		int y = (var1.getScaledHeight() / 2) - (50 / 2) + 60;
		int width = 12 + 1;
		int height = 9;

		if (!this.border) {
			BH.drawHUDRectWithBorder(x, y, width, height, 120, 120, 120, 150, 0, 0, 0, 255, .5f);
			BH.drawHUDRectWithBorder(x, y + 10, width + 21, height, 69, 69, 69, 150, 0, 0, 0, 255, .2f);
		} else {
			BH.drawHUDRectWithBorder(x, y + 10, width + 21, height, 120, 120, 120, 150, 0, 0, 0, 255, .5f);
			BH.drawHUDRectWithBorder(x, y, width, height, 69, 69, 69, 150, 0, 0, 0, 255, .2f);
		}

		BH.drawString(this.mc.fontRenderer, "BG", (var1.getScaledWidth() / 2) + (300 / 2), (var1.getScaledHeight() / 2) - (50 / 2) + 60, 0xffffff);
		BH.drawString(this.mc.fontRenderer, "Outline", (var1.getScaledWidth() / 2) + (300 / 2), (var1.getScaledHeight() / 2) - (50 / 2) + 70, 0xffffff);
		BH.drawString(this.mc.fontRenderer, "Toggle", (var1.getScaledWidth() / 2) + (300 / 2), (var1.getScaledHeight() / 2) - (50 / 2) + 80, 0xffffff);

		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);
	}

	private void renderSlider(int x, int y, String effector) {

		if (r > 255 || r < 0 || g > 255 || g < 0 || b > 255 || b < 0 || a > 255 || a < 0 || r1 > 255 || r1 < 0 || g1 > 255 || g1 < 0 || b1 > 255 || b1 < 0 || a1 > 255 || a1 < 0) {
			DW.setRGBA(Math.abs(r <= 255 ? r : 255), Math.abs(g <= 255 ? g : 255), Math.abs(b <= 255 ? b : 255), Math.abs(a <= 255 ? a : 255));
			DW.setBorderRGB(Math.abs(r1 <= 255 ? r1 : 255), Math.abs(g1 <= 255 ? g1 : 255), Math.abs(b1 <= 255 ? b1 : 255), Math.abs(a1 <= 255 ? a1 : 255));
			getInts();
		}

		BH.drawHUDRectWithBorder(x, y, 255, 5, 69, 69, 69, 180, 0, 0, 0, 255, .5f);
		glEnable(GL_TEXTURE_2D);
		y -= 10;
		if (!this.border) {
			if (effector.equalsIgnoreCase("Red"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + r, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Green"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + g, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Blue"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + b, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Alpha")) {
				BH.drawString(this.mc.fontRenderer, effector + ": " + a, x, y, 0xffffffff);
			} else {
			}

		} else {
			if (effector.equalsIgnoreCase("Red"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + r1, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Green"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + g1, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Blue"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + b1, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Alpha"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + a1, x, y, 0xffffffff);
			else if (effector.equalsIgnoreCase("Thickness"))
				BH.drawString(this.mc.fontRenderer, effector + ": " + thickness / 255, x, y, 0xffffffff);
		}
	}

	private void renderKnob(int x, int y, String color) {
		if (!this.border) {
			if (color.equalsIgnoreCase("red"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("green"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("blue"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("alpha"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("thickness"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 0, 0, 0, 0, 0, 0, 1f);
		} else {
			if (color.equalsIgnoreCase("red"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 0, 0, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("green"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 255, 0, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("blue"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 255, 255, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("alpha"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 255, 255, 255, 100, 0, 0, 0, 255, .3f);
			else if (color.equalsIgnoreCase("thickness"))
				BH.drawHUDRectWithBorder(x, y - 1, 2, 7, 0, 0, 0, 100, 255, 255, 255, 255, .3f);
		}
	}
}
