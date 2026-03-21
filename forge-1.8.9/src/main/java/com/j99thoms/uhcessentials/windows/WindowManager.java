package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.j99thoms.uhcessentials.BetterHUD;

public class WindowManager {
    public ArrayList<BaseWindow> BW;
    private FontRenderer FR;
    private Minecraft mc;
    private BetterHUD BH;
    private CoordsGUI cGui;
    public static boolean init;

    public CoordinateWindow CW;
    public CompassWindow Compass;
    public ClockWindow Clock;
    public ArrowCounterWindow arrow;
    public BiomeWindow biomeWindow;
    public ArmorWindow armorWindow;
    public TipWindow tipWindow;
    public FPSWindow FPS;
    // public KillCounterWindow kills;  // TODO: uncomment once KillCounterWindow is ported
    public static boolean toggled = true;

    private int showall = 0;

    public WindowManager(BetterHUD BH, FontRenderer FR, Minecraft mc) {
        init = false;
        this.FR = FR;
        this.mc = mc;
        this.BH = BH;
        BW = new ArrayList<BaseWindow>();
        init();
        BW.add(Compass);
        BW.add(Clock);
        BW.add(arrow);
        BW.add(CW);
        BW.add(biomeWindow);
        BW.add(armorWindow);
        BW.add(tipWindow);
        BW.add(FPS);
        // BW.add(kills);
        init = true;
    }

    public void reset() {
        showall = 0;
    }

    public void showAll() {
        showall = 1;
    }

    public void resetAllWindowsPositions() {
        for (int i = 0; i < BW.size(); i++) {
            BW.get(i).setToDefault();
        }
    }

    public void init() {
        CW = new CoordinateWindow(BH, FR, mc);
        cGui = new CoordsGUI(this, mc, BH);
        Compass = new CompassWindow(BH, mc);
        biomeWindow = new BiomeWindow(BH, mc, CW);
        Clock = new ClockWindow(BH, mc);
        arrow = new ArrowCounterWindow(BH, mc);
        FPS = new FPSWindow(BH, mc, CW);
        armorWindow = new ArmorWindow(BH, mc, CW);
        tipWindow = new TipWindow(BH, mc, CW);
        // kills = new KillCounterWindow(BH, FR, mc, CW);
    }

    public void update() {
        if (toggled) {
            for (int i = 0; i < BW.size(); i++) {
                if (BW.get(i).getToggled() == 1 || showall == 1)
                    BW.get(i).update();
            }
        }
        // cGui.update() runs regardless of toggled state so the config GUI
        // remains responsive even when HUD windows are hidden
        if (init) {
            cGui.update();
        }
    }

    public void render() {
        if (toggled) {
            for (int i = 0; i < BW.size(); i++) {
                if (BW.get(i).getToggled() == 1 || showall == 1)
                    BW.get(i).render();
            }
        }
    }
}
