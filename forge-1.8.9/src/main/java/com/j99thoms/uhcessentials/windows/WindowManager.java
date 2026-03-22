package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.j99thoms.uhcessentials.BetterHUD;

public class WindowManager {
    public ArrayList<BaseWindow> windows;
    private FontRenderer fontRenderer;
    private Minecraft mc;
    private BetterHUD betterHUD;
    private CoordsGUI coordsGUI;
    public static boolean init;

    public CoordinateWindow coordWindow;
    public CompassWindow compassWindow;
    public ClockWindow clockWindow;
    public ArrowCounterWindow arrowWindow;
    public BiomeWindow biomeWindow;
    public ArmorWindow armorWindow;
    public TipWindow tipWindow;
    public FPSWindow fpsWindow;
    // public KillCounterWindow kills;  // TODO: uncomment once KillCounterWindow is ported
    public static boolean toggled = true;

    private int showAllOverride = 0;

    public WindowManager(BetterHUD betterHUD, FontRenderer fontRenderer, Minecraft mc) {
        init = false;
        this.fontRenderer = fontRenderer;
        this.mc = mc;
        this.betterHUD = betterHUD;
        windows = new ArrayList<BaseWindow>();
        init();
        windows.add(compassWindow);
        windows.add(clockWindow);
        windows.add(arrowWindow);
        windows.add(coordWindow);
        windows.add(biomeWindow);
        windows.add(armorWindow);
        windows.add(tipWindow);
        windows.add(fpsWindow);
        // windows.add(kills);
        init = true;
    }

    public void reset() {
        showAllOverride = 0;
    }

    public void showAll() {
        showAllOverride = 1;
    }

    public void resetAllWindowsPositions() {
        for (int i = 0; i < windows.size(); i++) {
            windows.get(i).setToDefault();
        }
    }

    public void init() {
        coordWindow = new CoordinateWindow(betterHUD, fontRenderer, mc);
        coordsGUI = new CoordsGUI(this, mc, betterHUD);
        compassWindow = new CompassWindow(betterHUD, mc);
        biomeWindow = new BiomeWindow(betterHUD, mc, coordWindow);
        clockWindow = new ClockWindow(betterHUD, mc);
        arrowWindow = new ArrowCounterWindow(betterHUD, mc);
        fpsWindow = new FPSWindow(betterHUD, mc, coordWindow);
        armorWindow = new ArmorWindow(betterHUD, mc, coordWindow);
        tipWindow = new TipWindow(betterHUD, mc, coordWindow);
        // kills = new KillCounterWindow(betterHUD, fontRenderer, mc, coordWindow);
    }

    public void update() {
        if (toggled) {
            for (int i = 0; i < windows.size(); i++) {
                if (windows.get(i).getToggled() == 1 || showAllOverride == 1)
                    windows.get(i).update();
            }
        }
        // coordsGUI.update() runs regardless of toggled state so the config GUI
        // remains responsive even when HUD windows are hidden
        if (init) {
            coordsGUI.update();
        }
    }

    public void render() {
        if (toggled) {
            for (int i = 0; i < windows.size(); i++) {
                if (windows.get(i).getToggled() == 1 || showAllOverride == 1)
                    windows.get(i).render();
            }
        }
    }
}
