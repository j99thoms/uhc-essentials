package com.sintinium.BetterHUD.windows;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sintinium.BetterHUD.BetterHUD;

public class FPSWindow extends DefaultWindow {

	BetterHUD BH;

	private int x = 2;
	private int y = 34;

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
	private int flash = 0;

	private float thickness = .8f;
	private ArrayList<Double> data = new ArrayList<Double>();
	private FileManager FM;
	private ItemStack[] inventroy = new ItemStack[36];
	private Item item;
	private int nextFlash = 2;
	private boolean shouldFlash = true;
	private int count = 0;
	private int timer = 0;
	private int secondTimer = 0;
	private int fpsCount = 0;

	private Minecraft mc;
	private CoordinateWindow CW;

	private double toggle = 1;

	public FPSWindow(BetterHUD BH, Minecraft mc, CoordinateWindow CW) {
		this.mc = mc;
		this.BH = BH;
		this.CW = CW;
		FM = new FileManager("FPSWindow", 3);
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
		setY(34);
		save();
	}

	public int getToggled() {
		return (int) toggle;
	}

	public void update() {
		
		String debug = this.mc.debug;
		String[] fps = debug.split(" ");
		fpsCount = Integer.parseInt(fps[0]);
		String info = fpsCount + " FPS";
		width = this.mc.fontRenderer.getStringWidth(info + "");
		BH.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2, CW.getR(), CW.getG(), CW.getB(), CW.getA(), CW.getBorderR(), CW.getBorderG(), CW.getBorderB(), CW.getBorderA(), CW.getThickness());
		this.mc.fontRenderer.drawStringWithShadow(info, x, y, 0xffffffff);
		
		if(this.toggle == 0) {
			this.mc.fontRenderer.drawStringWithShadow("X", x - 5, y - 5, 0xffffffff);
		}

	}

	public String getName() {
		return "FPSWindow";
	}

	public void render() {
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
		return width;
	}

	public int getHeight() {
		return 8;
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
