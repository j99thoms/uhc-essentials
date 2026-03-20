package com.sintinium.BetterHUD.windows;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Direction;
import net.minecraft.util.MathHelper;

import org.lwjgl.input.Keyboard;

import com.sintinium.BetterHUD.BetterHUD;

public class CoordinateWindow extends DefaultWindow {
	private BetterHUD BH;
	private FontRenderer FR;
	private Minecraft mc;

	private int x = 2;
	private int y = 2;

	private int r = 69;
	private int g = 69;
	private int b = 69;
	private int a = 150;

	private int r1 = 0;
	private int g1 = 0;
	private int b1 = 0;
	private int a1 = 255;

	private String direction;
	private String xEx;
	private String zEx;

	private double toggle = 1;

	private int width;
	private int height = 30;
	private double thickness = .8f;
	float scale = .75F;

	private FileManager FM;
	private ArrayList<Double> data = new ArrayList<Double>();

	public CoordinateWindow(BetterHUD BH, FontRenderer FR, Minecraft mc) {
		this.mc = mc;
		this.BH = BH;
		this.FR = FR;
		FM = new FileManager("Coord", 12);
		load();
	}

	public void toggle() {
		if (toggle == 0) {
			toggle = 1;
		} else {
			toggle = 0;
		}
	}
	
	public void setToDefault() {
		setX(2);
		setY(2);
		save();
	}

	public int getToggled() {
		return (int) toggle;
	}

	public String getName() {
		return "Coordinate";
	}

	public void update() {
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void save() {
		data.clear();
		data.add((double) x);
		data.add((double) y);
		data.add((double) r);
		data.add((double) g);
		data.add((double) b);
		data.add((double) a);
		data.add((double) r1);
		data.add((double) g1);
		data.add((double) b1);
		data.add((double) a1);
		data.add(thickness);
		data.add(toggle);
		FM.setArray(data);
	}

	public void load() {
		data.clear();
		data = FM.getArray();
		if (data.size() < 12) {
			save();
		} else {
			x = data.get(0).intValue();
			y = data.get(1).intValue();
			r = data.get(2).intValue();
			g = data.get(3).intValue();
			b = data.get(4).intValue();
			a = data.get(5).intValue();
			r1 = data.get(6).intValue();
			g1 = data.get(7).intValue();
			b1 = data.get(8).intValue();
			a1 = data.get(9).intValue();
			thickness = data.get(10);
			toggle = data.get(11);
		}
	}

	public void render() {
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		drawCoordPane();
		glDepthMask(true);
		glDisable(GL_BLEND);
		glEnable(GL_TEXTURE_2D);

		if (getToggled() == 0) {
			this.mc.fontRenderer.drawString("X", getX() + getWidth() - 3, getY() - 3, 0xffffffff);
		}
	}

	private void drawCoordPane() {
		double x = Math.floor(mc.thePlayer.posX);
		double y = Math.floor(mc.thePlayer.posY - 1);
		double z = Math.floor(mc.thePlayer.posZ);
		int var25 = MathHelper.floor_double((double) (this.mc.thePlayer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		String direction = Direction.directions[var25];

		double rotation = MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw);

		if (direction.equalsIgnoreCase("north")) {
			direction = direction + " -Z";
		} else if (direction.equalsIgnoreCase("east")) {
			direction = direction + " +X";
		} else if (direction.equalsIgnoreCase("south")) {
			direction = direction + " +Z";
		} else {
			direction = direction + " -X";
		}

		int width;

		if (x < 1000 && y < 1000 && z < 1000 && x > -1000 && y > -1000 && z > -1000)
			width = 54;
		else if (x > -10000 && y > -10000 & z > -10000 && x < 10000 && y < 10000 && z < 10000) {
			width = 60;
		} else if (x > -100000 && y > -100000 && z > -100000 && x < 100000 && y < 100000 && z < 100000) {
			width = 66;
		} else if (x > -1000000 && y > -1000000 && z > -1000000 && x < 1000000 && y < 1000000 && z < 1000000) {
			width = 72;
		} else if (x > -10000000 && y > -10000000 && z > -10000000 && x < 10000000 && y < 10000000 && z < 10000000) {
			width = 78;
		} else if (x > -100000000 && y > -100000000 && z > -100000000 && x < 100000000 && y < 100000000 && z < 100000000) {
			width = 98;
		} else {
			// BH.drawHUDRectWithBorder(1, 1, 86, 40, 69, 69, 69, 180, 0, r2,
			// b2, alpha2, .8F);
			width = 86;
		}

		this.width = width;
		BH.drawHUDRectWithBorder(this.x - 1, this.y - 1, width, this.height, r, g, b, a, r1, g1, b1, a1, thickness);
		glEnable(GL_TEXTURE_2D);

		if (rotation > -22.5 && rotation <= 22.5) {
			this.direction = "S";
			this.zEx = "+";
		} else if (rotation > 22.5 && rotation <= 22.5 * 3) {
			this.direction = "SW";
			this.zEx = "+";
			this.xEx = "-";
		} else if (rotation > 22.5 * 3 && rotation <= 22.5 * 5) {
			this.direction = "W";
			this.xEx = "-";
		} else if (rotation > 22.5 * 5 && rotation <= 22.5 * 7) {
			this.direction = "NW";
			this.zEx = "-";
			this.xEx = "-";
		} else if ((rotation > 22.5 * 7 && rotation <= 22.5 * 9) || (rotation > -180 && rotation <= -180 + 22.5)) {
			this.direction = "N";
			this.zEx = "-";
		} else if (rotation > -180 + 22.5 && rotation <= -180 + 22.5 * 3) {
			this.direction = "NE";
			this.zEx = "-";
			this.xEx = "+";
		} else if (rotation > -180 + 22.5 * 3 && rotation <= -180 + 22.5 * 5) {
			this.direction = "E";
			this.xEx = "+";
		} else if (rotation > -180 + 22.5 * 5 && rotation <= -180 + 22.5 * 7) {
			this.direction = "SE";
			this.zEx = "+";
			this.xEx = "+";
		}

		this.drawString(FR, "X: " + (int) x, this.x, this.y, 0xffffff);
		this.drawString(FR, "Y: " + (int) y, this.x, this.y + 10, 0xffffff);
		this.drawString(FR, "Z: " + (int) z, this.x, this.y + 20, 0xffffff);

		int tempx = getX() + getWidth() - 12;
		int dLength = this.mc.fontRenderer.getStringWidth(this.direction) / 2;

		this.drawString(FR, xEx, tempx, this.y, 0xffffff);
		this.drawString(FR, this.direction, tempx - dLength + 3, this.y + 10, 0xffffff);
		this.drawString(FR, zEx, tempx, this.y + 20, 0xffffff);
		// this.drawString(FR, direction, this.x, this.y + 30, 0xffffff);
		glDisable(GL_TEXTURE_2D);
		xEx = "";
		zEx = "";
	}

	public void setRGBA(int r, int g, int b, int a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public int getR() {
		return r;
	}

	public int getG() {
		return g;
	}

	public int getB() {
		return b;
	}

	public int getA() {
		return a;
	}

	public void setBorderRGB(int r, int g, int b, int a) {
		this.r1 = r;
		this.g1 = g;
		this.b1 = b;
		this.a1 = a;
	}

	public int getBorderR() {
		return r1;
	}

	public int getBorderG() {
		return g1;
	}

	public int getBorderB() {
		return b1;
	}

	public int getBorderA() {
		return a1;
	}

	public void setThickness(float t) {
		this.thickness = t;
	}

	public double getThickness() {
		return this.thickness;
	}
}
