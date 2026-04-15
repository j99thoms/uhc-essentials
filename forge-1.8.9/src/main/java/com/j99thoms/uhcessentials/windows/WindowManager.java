package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;
import java.util.List;

import com.j99thoms.uhcessentials.GameContext;
import com.j99thoms.uhcessentials.HUDGraphics;

public class WindowManager {
    private ArrayList<BaseWindow> windows;
    private final HUDGraphics hudGraphics;
    private final GameContext gameContext;

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
    public static boolean configScreenOpen = false;

    private int showAllOverride = 0;

    public WindowManager(HUDGraphics hudGraphics, GameContext gameContext) {
        this.hudGraphics = hudGraphics;
        this.gameContext = gameContext;
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
        coordWindow = new CoordinateWindow(hudGraphics, gameContext, theme);
        compassWindow = new CompassWindow(hudGraphics);
        biomeWindow = new BiomeWindow(hudGraphics, gameContext, theme);
        clockWindow = new ClockWindow(hudGraphics);
        arrowWindow = new ArrowCounterWindow(hudGraphics, gameContext);
        fpsWindow = new FPSWindow(hudGraphics, gameContext, theme);
        armorWindow = new ArmorWindow(hudGraphics, gameContext, theme);
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
    }

    public List<BaseWindow> getWindows()     { return windows; }
    public TipWindow getTipWindow()          { return tipWindow; }
    public WindowTheme getTheme()            { return theme; }
    public static boolean isToggled()        { return toggled; }
    public static void setToggled(boolean t) { toggled = t; }
}
