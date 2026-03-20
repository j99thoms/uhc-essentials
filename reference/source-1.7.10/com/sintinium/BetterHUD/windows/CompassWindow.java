package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.sintinium.BetterHUD.BetterHUD;

public class CompassWindow extends DefaultWindow {
	BetterHUD BH;

	private int x = 1;
	private int y = 56;

	private int r = 69;
	private int g = 69;
	private int b = 69;
	private int a = 150;

	private int r1 = 0;
	private int g1 = 0;
	private int b1 = 0;
	private int a1 = 255;

	private int width = 0;
	private int height = 0;

	public static double toggle = 1;

	private float thickness = .8f;

	private ArrayList<Double> data = new ArrayList<Double>();
	private FileManager FM;

	private Minecraft mc;

	public CompassWindow(BetterHUD BH, Minecraft mc) {
		this.mc = mc;
		this.BH = BH;
		FM = new FileManager("Compass", 3);
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
		setX(1);
		setY(56);
		save();
	}

	public int getToggled() {
		return (int) toggle;
	}

	@Override
	public void update() {
		BH.drawItemSprite(x, y, 345, this);
		if ((int) toggle == 0)
			this.mc.fontRenderer.drawStringWithShadow("X", x, y, 0xffffffff);
	}

	public String getName() {
		return "Compass";
	}

	public void render() {
		// drawItemSprite(0, 45, 347);
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

	public void setRGBA(int r, int g, int b, int a) {
	}

	public int getR() {
		return 0;
	}

	public int getG() {
		return 0;
	}

	public int getB() {
		return 0;
	}

	@Override
	public int getA() {
		return 0;
	}

	public void setBorderRGB(int r, int g, int b, int a) {
	}

	public int getBorderR() {
		return 0;
	}

	public int getBorderG() {
		return 0;
	}

	public int getBorderB() {
		return 0;
	}

	public int getBorderA() {
		return 0;
	}

	public void setThickness(float t) {
	}

	public double getThickness() {
		return 0;
	}

	public int getWidth() {
		return 14;
	}

	public int getHeight() {
		return 12;
	}

	public void save() {
		data.clear();
		data.add((double) x);
		data.add((double) y);
		data.add((double) toggle);
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
			toggle = data.get(2).intValue();
		}
	}
}
