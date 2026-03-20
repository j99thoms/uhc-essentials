package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.world.chunk.Chunk;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.FileManager;

public class BiomeWindow extends DefaultWindow {
	BetterHUD BH;

	private int x = 2;
	private int y = 46;

	private int r = 69;
	private int g = 69;
	private int b = 69;
	private int a = 150;

	private int r1 = 0;
	private int g1 = 0;
	private int b1 = 0;
	private int a1 = 255;

	private int width = 0;
	private int height = 12;

	private double toggle = 1;

	private float thickness = .8f;

	private ArrayList<Double> data = new ArrayList<Double>();
	private FileManager FM;
	private CoordinateWindow CW;

	private Minecraft mc;

	public BiomeWindow(BetterHUD BH, Minecraft mc, CoordinateWindow CW) {
		this.mc = mc;
		this.BH = BH;
		this.CW = CW;
		FM = new FileManager("BiomeWindow", 3);
		load();
	}
	
	public void setToDefault() {
		setX(2);
		setY(46);
		save();
	}

	public void toggle() {
		if (toggle == 0) {
			toggle = 1;
		} else {
			toggle = 0;
		}
	}

	public int getToggled() {
		return (int) toggle;
	}

	@Override
	public void update() {
		
		int var22 = (int) Math.floor(this.mc.thePlayer.posX);
		int var24 = (int) Math.floor(this.mc.thePlayer.posZ);
		Chunk var26 = this.mc.theWorld.getChunkFromBlockCoords(var22, var24);
		
		String biomeName = var26.getBiomeGenForWorldCoords(var22 & 15, var24 & 15, this.mc.theWorld.getWorldChunkManager()).biomeName;
		
		width = this.mc.fontRenderer.getStringWidth(biomeName);
		BH.drawHUDRectWithBorder(x - 1, y - 1, this.mc.fontRenderer.getStringWidth(biomeName) + 1, this.getHeight(), CW.getR(), CW.getG(), CW.getB(), CW.getA(), CW.getBorderR(), CW.getBorderG(), CW.getBorderB(), CW.getBorderA(), CW.getThickness());
		this.mc.fontRenderer.drawStringWithShadow(biomeName, x, y, 0xffffffff);
		
		if ((int) toggle == 0)
			this.mc.fontRenderer.drawStringWithShadow("X", getX() - 5, getY() - 5, 0xffffffff);
	}

	public String getName() {
		return "BiomeWindow";
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
		return width + 1;
	}

	public int getHeight() {
		return 10;
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
			toggle = data.get(2);
		}
	}
}
