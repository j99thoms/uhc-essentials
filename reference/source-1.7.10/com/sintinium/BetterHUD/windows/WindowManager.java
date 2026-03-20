package com.sintinium.BetterHUD.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.sintinium.BetterHUD.BetterHUD;

public class WindowManager {
	public ArrayList<DefaultWindow> DW;
	private FontRenderer FR;
	private Minecraft mc;
	private BetterHUD BH;
	private CoordsGUI cGui;
	public static boolean init;

	public CoordinateWindow CW;
	public CompassWindow Compass;
	public ClockWindow Clock;
	public ArrowCounterWindow arrow;
	// public KillCounterWindow kills;
	public BiomeWindow biomeWindow;
	public FPSWindow FPS;
	public static boolean toggled = true;

	private int showall = 0;

	public WindowManager(BetterHUD BH, FontRenderer FR, Minecraft mc) {
		init = false;
		this.FR = FR;
		this.mc = mc;
		this.BH = BH;
		DW = new ArrayList<DefaultWindow>();
		init();
		DW.add(Compass);
		DW.add(Clock);
		DW.add(arrow);
		// DW.add(kills);
		DW.add(CW);
		DW.add(biomeWindow);
		DW.add(FPS);
		init = true;
	}

	public void reset() {
		showall = 0;
	}

	public void showAll() {
		showall = 1;
	}
	
	public void resetAllWindowsPositions() {
		for(int i = 0; i < DW.size(); i++) {
			DW.get(i).setToDefault();
		}
	}

	public void init() {
		CW = new CoordinateWindow(BH, FR, mc);
		this.cGui = new CoordsGUI(this, mc, BH);
		// kills = new KillCounterWindow(BH, FR, mc, CW);
		Compass = new CompassWindow(BH, mc);
		biomeWindow = new BiomeWindow(BH, mc, CW);
		Clock = new ClockWindow(BH, mc);
		arrow = new ArrowCounterWindow(BH, mc);
		FPS = new FPSWindow(BH, mc, CW);
	}

	public void update() {
		if (toggled) {
			for (int i = 0; i < DW.size(); i++) {
				if (DW.get(i).getToggled() == 1 || showall == 1)
					DW.get(i).update();
			}

			if (init) {
				cGui.update();
			}
		}
	}

	public void render() {
		if (toggled) {
			for (int i = 0; i < DW.size(); i++) {
				if (DW.get(i).getToggled() == 1 || showall == 1)
					DW.get(i).render();
			}
		}
	}
}
