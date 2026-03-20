package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.sintinium.BetterHUD.BetterHUD;

public class OptionMenu extends GuiScreen {

	private Minecraft mc;
	private CoordsGUI cGui;
	private BetterHUD BH;

	private FileManager FM;
	private ArrayList<Double> data = new ArrayList<Double>();
	private double drag;
	private double bright;

	private int x1, x2, y1, y2, width1, width2, height1, height2;

	private String text1;
	private String text2;
	private double button1;
	private double button2;
	private boolean set1;
	private boolean set2;
	
	String func1 = "Draggable HUD";
	String func2 = "Full Gamma Bright";
	
	public OptionMenu(Minecraft mc, CoordsGUI cGui, BetterHUD BH) {
		this.mc = mc;
		this.cGui = cGui;
		this.BH = BH;
		width1 = 40;
		width2 = 40;
		height1 = 10;
		height2 = 10;

		FM = new FileManager("keys.txt", 2);
		data = FM.getArray();
		this.drag = (int) (double) this.data.get(0);
		this.bright = (int) (double) this.data.get(1);
		
		text1 = Keyboard.getKeyName((int) drag);
		text2 = Keyboard.getKeyName((int) bright);
	}

	public void render() {
		if (Mouse.isButtonDown(0) && !set1 && !set2) {
			mouse();
		} else if (set1) {
			if (Keyboard.getEventKeyState()) {
				button1 = Keyboard.getEventKey();
				text1 = Keyboard.getKeyName((int) button1);
				set1 = false;
				data.set(0, button1);
				save();
			}
		} else if (set2) {
			if (Keyboard.getEventKeyState()) {
				button2 = Keyboard.getEventKey();
				text2 = Keyboard.getKeyName((int) button2);
				set2 = false;
				data.set(1, button2);
				save();
			}
		}
		ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		x1 = var1.getScaledWidth() / 2 - width1 / 2;
		y1 = var1.getScaledHeight() / 2 - height1 / 2 - 22;
		x2 = var1.getScaledWidth() / 2 - width2 / 2;
		y2 = var1.getScaledHeight() / 2 - height2 / 2 + 22;

		this.BH.drawHUDRectWithBorder(x1, y1, width1, height1, 0, 0, 0, 255, 255, 255, 255, 255, 1.5f);
		this.mc.fontRenderer.drawStringWithShadow(func1, x1 - this.mc.fontRenderer.getStringWidth(func1) / 4, y1 - 12, 0xffffffff);
		this.BH.drawHUDRectWithBorder(x2, y2, width2, height2, 0, 0, 0, 255, 255, 255, 255, 255, 1.5f);
		this.mc.fontRenderer.drawStringWithShadow(func2, x2 - this.mc.fontRenderer.getStringWidth(func2) / 4, y2 - 12, 0xffffffff);
		if (!set1 && !set2) {
			this.mc.fontRenderer.drawStringWithShadow(text1, x1 + 1, y1 + 1, 0xffffffff);
			this.mc.fontRenderer.drawStringWithShadow(text2, x2 + 1, y2 + 1, 0xffffffff);
		} else if (set1) {
			this.mc.fontRenderer.drawStringWithShadow(text1, x1 + 1, y1 + 1, 0xffff0000);
			this.mc.fontRenderer.drawStringWithShadow(text2, x2 + 1, y2 + 1, 0xffffffff);
		} else if (set2) {
			this.mc.fontRenderer.drawStringWithShadow(text1, x1 + 1, y1 + 1, 0xffffffff);
			this.mc.fontRenderer.drawStringWithShadow(text2, x2 + 1, y2 + 1, 0xffff0000);
		}
	}

	public void reset() {
		set1 = false;
		set2 = false;
	}
	
	public void save() {
		FM.setArray(data);
	}

	public void mouse() {
		ScaledResolution var1 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
		// int x = Mouse.getX();
		// int y = Mouse.getY();
		int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
		int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

		if (x >= x1 && x <= x1 + width1 && y >= y1 && y <= y1 + height1) {
			set1 = true;
		}
		if (x >= x2 && x <= x2 + width2 && y >= y2 && y <= y2 + height2) {
			set2 = true;
		}
	}

}
