package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.sintinium.BetterHUD.BetterHUD;

public class KillCounterWindow extends DefaultWindow {

	private BetterHUD BH;
	private FontRenderer FR;
	private Minecraft mc;
	private CoordinateWindow CW;

	private int x = 3;
	private int y = 35;

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
	private int height = 10;
	private double thickness = .8f;
	float scale = .75F;

	public static int kills;
	private FileManager FM;
	private ArrayList<Double> data = new ArrayList<Double>();

	public KillCounterWindow(BetterHUD BH, FontRenderer FR, Minecraft mc, CoordinateWindow CW) {
		this.mc = mc;
		this.BH = BH;
		this.FR = FR;
		this.CW = CW;
		FM = new FileManager("KillCounter", 2);
		load();
	}
	
	public void setToDefault() {
		setX(3);
		setY(35);
		save();
	}

	public void update() {
		this.r = CW.getR();
		this.g = CW.getG();
		this.b = CW.getB();
		this.a = CW.getA();
		this.r1 = CW.getBorderR();
		this.g1 = CW.getBorderG();
		this.b1 = CW.getBorderB();
		this.a1 = CW.getBorderA();
		this.thickness = CW.getThickness();
	}

	public void render() {
		String text = "Kills: " + kills;
		width = this.mc.fontRenderer.getStringWidth(text) + 10;
		BH.drawHUDRectWithBorder(x - 2, y - 1, width, height, r, g, b, a, r1, g1, b1, a1, thickness);
		this.mc.fontRenderer.drawStringWithShadow("Kills: " + kills, x, y, 0xffffffff);
		if(toggle == 0) {			
			this.mc.fontRenderer.drawString("X", x - 5, y - 5, 0xffffffff);
		}
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setRGBA(int r, int g, int b, int a) {
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
	}

	public double getThickness() {
		return thickness;
	}

	public void save() {
		data.clear();
		data.add((double) x);
		data.add((double) y);
		data.add(toggle);
		FM.setArray(data);
	}

	public void load() {
		data.clear();
		data = FM.getArray();
		if (data.size() < 3) {
			save();
		} else {
			x = data.get(0).intValue();
			y = data.get(1).intValue();
			toggle = data.get(2);
		}
	}

	public String getName() {
		return "kills";
	}

	public void toggle() {
		if (this.toggle == 1)
			this.toggle = 0;
		else
			this.toggle = 1;
	}

	public int getToggled() {
		return (int) toggle;
	}

}
