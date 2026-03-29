package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.HUDGraphics;

public class WindowManager {
    ArrayList<BaseWindow> windows;
    private final Minecraft mc;
    private final HUDGraphics hudGraphics;
    private HUDConfigScreen hudConfigScreen;
    static boolean init;

    WindowTheme theme;
    CoordinateWindow coordWindow;
    CompassWindow compassWindow;
    ClockWindow clockWindow;
    ArrowCounterWindow arrowWindow;
    BiomeWindow biomeWindow;
    ArmorWindow armorWindow;
    TipWindow tipWindow;
    FPSWindow fpsWindow;
    static boolean toggled = true;

    private int showAllOverride = 0;

    public WindowManager(HUDGraphics hudGraphics, Minecraft mc) {
        init = false;
        this.mc = mc;
        this.hudGraphics = hudGraphics;
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
        theme = new WindowTheme("Theme"); // One shared theme for all windows
        coordWindow = new CoordinateWindow(hudGraphics, mc, theme);
        hudConfigScreen = new HUDConfigScreen(this, mc, hudGraphics);
        compassWindow = new CompassWindow(hudGraphics);
        biomeWindow = new BiomeWindow(hudGraphics, mc, theme);
        clockWindow = new ClockWindow(hudGraphics);
        arrowWindow = new ArrowCounterWindow(hudGraphics, mc);
        fpsWindow = new FPSWindow(hudGraphics, theme);
        armorWindow = new ArmorWindow(hudGraphics, mc, theme);
        tipWindow = new TipWindow(hudGraphics, theme);
    }

    public void update() {
        if (toggled) {
            for (int i = 0; i < windows.size(); i++) {
                if (windows.get(i).getToggled() == 1 || showAllOverride == 1)
                    windows.get(i).update();
            }
        }
    }

    public void render() {
        if (toggled) {
            for (int i = 0; i < windows.size(); i++) {
                if (windows.get(i).getToggled() == 1 || showAllOverride == 1)
                    windows.get(i).render();
            }
        }
        // hudConfigScreen.update() runs regardless of toggled state so the config GUI
        // remains responsive even when HUD windows are hidden.
        // Called from render() (not update()) so its GL drawing calls run on the render thread.
        if (init) {
            hudConfigScreen.update();
        }
    }
}
