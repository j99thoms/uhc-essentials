package com.sintinium.BetterHUD.windows;

import net.minecraft.client.Minecraft;

import com.sintinium.BetterHUD.BetterHUD;

public class Slider {
	
	private int initValue;
	private String name;
	private BetterHUD BH;
	private Minecraft mc;
	private int y;
	
	public Slider(int initValue, int y, String name, BetterHUD BH, Minecraft mc) {
		this.initValue = initValue;
		this.name = name;
		this.BH = BH;
		this.mc = mc;
	}
	
	public void drawSlider() {
		
	}

}
