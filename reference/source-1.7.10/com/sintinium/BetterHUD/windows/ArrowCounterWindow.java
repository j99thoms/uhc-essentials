package com.sintinium.BetterHUD.windows;

import static org.lwjgl.opengl.GL11.*;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import net.minecraft.client.Minecraft;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.sintinium.BetterHUD.BetterHUD;

public class ArrowCounterWindow extends DefaultWindow {

	BetterHUD BH;

	private int x = 16;
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

	private Minecraft mc;

	private double toggle = 1;

	public ArrowCounterWindow(BetterHUD BH, Minecraft mc) {
		this.mc = mc;
		this.BH = BH;
		FM = new FileManager("Arrow", 3);
		load();
	}
	
	public void setToDefault() {
		setX(16);
		setY(56);
		save();
	}

	public void toggle() {
		if (toggle == 0) {
			toggle = 1;
		} else if(toggle == 1){
			toggle = 2;
		} else {
			toggle = 0;
		}
	}

	public int getToggled() {
		if(toggle == 2) {
			return 1;
		} else
			return (int) toggle;
	}

	public void update() {
		count = 0;
		this.inventroy = mc.thePlayer.inventory.mainInventory;
		for (int i = 0; i < inventroy.length; i++) {
			if (this.inventroy[i] != null) {
				item = this.inventroy[i].getItem();
				if (item.getIdFromItem(item) == 262)
					count += this.inventroy[i].stackSize;
			}
		}
		
		glEnable(GL_BLEND);
		glDepthMask(false);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		if (count <= 5 && this.shouldFlash) {
			timer++;
			BH.drawHUDRectWithBorder(getX() + 2, getY() + 2, getWidth() + 1, getHeight() + 1, 255, 0, 0, 150, 0, 0, 0, 100, .5);

			if (timer > 50) {
				this.shouldFlash = false;
			}
		} else if (count > 5) {
			timer = 0;
			this.shouldFlash = true;
			this.nextFlash = 4;
		}

		BH.drawItemSprite(x, y, 262, this);
		if(toggle != 2 || count < 64)
			this.mc.fontRenderer.drawStringWithShadow(count + "", x + 11, y + 9, 0xffffffff);
		else if(count > 64 && count % 64 != 0)
			this.mc.fontRenderer.drawStringWithShadow("[" + (int) Math.floor(count / 64) + "]+" + count % 64, x + 11, y + 9, 0xffffffff);
		else if(count >= 64 && count % 64 == 0)
			this.mc.fontRenderer.drawStringWithShadow("[" + (int) Math.floor(count / 64) + "]", x + 11, y + 9, 0xffffffff);
			
		glDepthMask(true);
		glDisable(GL_BLEND);
		
		if((int)toggle == 0)
			this.mc.fontRenderer.drawStringWithShadow("X", x, y, 0xffffffff);
		if((int)toggle == 1 && CoordsGUI.guiOpen)
			this.mc.fontRenderer.drawStringWithShadow("Sum", x, y, 0xffffffff);
		if((int) toggle == 2 && CoordsGUI.guiOpen)
			this.mc.fontRenderer.drawStringWithShadow("Stacks", x, y, 0xffffffff);
	}

	public String getName() {
		return "ArrowCounter";
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
		return 13;
	}

	public int getHeight() {
		return 13;
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
