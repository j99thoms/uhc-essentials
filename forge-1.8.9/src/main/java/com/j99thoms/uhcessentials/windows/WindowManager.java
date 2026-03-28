package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.j99thoms.uhcessentials.BetterHUD;

public class WindowManager {
    ArrayList<BaseWindow> windows;
    private final FontRenderer fontRenderer;
    private final Minecraft mc;
    private final BetterHUD betterHUD;
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
    // KillCounterWindow kills;  // TODO: uncomment once KillCounterWindow is ported
    static boolean toggled = true;

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
        theme = new WindowTheme("Theme"); // One shared theme for all windows
        coordWindow = new CoordinateWindow(betterHUD, mc, theme);
        hudConfigScreen = new HUDConfigScreen(this, mc, betterHUD);
        compassWindow = new CompassWindow(betterHUD, mc);
        biomeWindow = new BiomeWindow(betterHUD, mc, theme);
        clockWindow = new ClockWindow(betterHUD, mc);
        arrowWindow = new ArrowCounterWindow(betterHUD, mc);
        fpsWindow = new FPSWindow(betterHUD, mc, theme);
        armorWindow = new ArmorWindow(betterHUD, mc, theme);
        tipWindow = new TipWindow(betterHUD, mc, theme);
        // kills = new KillCounterWindow(betterHUD, fontRenderer, mc, theme);
    }

    public void update() {
        if (toggled) {
            for (int i = 0; i < windows.size(); i++) {
                if (windows.get(i).getToggled() == 1 || showAllOverride == 1)
                    windows.get(i).update();
            }
        }
        // hudConfigScreen.update() runs regardless of toggled state so the config GUI
        // remains responsive even when HUD windows are hidden
        if (init) {
            hudConfigScreen.update();
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
