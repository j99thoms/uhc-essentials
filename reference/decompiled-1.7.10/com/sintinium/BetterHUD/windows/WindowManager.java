/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  bao
 *  bbu
 */
package com.sintinium.BetterHUD.windows;

import com.sintinium.BetterHUD.BetterHUD;
import com.sintinium.BetterHUD.windows.ArmorWindow;
import com.sintinium.BetterHUD.windows.ArrowCounterWindow;
import com.sintinium.BetterHUD.windows.BiomeWindow;
import com.sintinium.BetterHUD.windows.ClockWindow;
import com.sintinium.BetterHUD.windows.CompassWindow;
import com.sintinium.BetterHUD.windows.CoordinateWindow;
import com.sintinium.BetterHUD.windows.CoordsGUI;
import com.sintinium.BetterHUD.windows.DefaultWindow;
import com.sintinium.BetterHUD.windows.EntityWindow;
import com.sintinium.BetterHUD.windows.FPSWindow;
import com.sintinium.BetterHUD.windows.TipWindow;
import java.util.ArrayList;

public class WindowManager {
    public ArrayList<DefaultWindow> DW;
    private bbu FR;
    private bao mc;
    private BetterHUD BH;
    private CoordsGUI cGui;
    public static boolean init;
    public CoordinateWindow CW;
    public CompassWindow Compass;
    public ClockWindow Clock;
    public ArrowCounterWindow arrow;
    public BiomeWindow biomeWindow;
    public EntityWindow entityWindow;
    public ArmorWindow armorWindow;
    public TipWindow tipWindow;
    public FPSWindow FPS;
    public static boolean toggled;
    private int showall = 0;

    public WindowManager(BetterHUD BH, bbu FR, bao mc) {
        init = false;
        this.FR = FR;
        this.mc = mc;
        this.BH = BH;
        this.DW = new ArrayList();
        this.init();
        this.DW.add(this.Compass);
        this.DW.add(this.Clock);
        this.DW.add(this.arrow);
        this.DW.add(this.CW);
        this.DW.add(this.biomeWindow);
        this.DW.add(this.entityWindow);
        this.DW.add(this.armorWindow);
        this.DW.add(this.tipWindow);
        this.DW.add(this.FPS);
        init = true;
    }

    public void reset() {
        this.showall = 0;
    }

    public void showAll() {
        this.showall = 1;
    }

    public void resetAllWindowsPositions() {
        for (int i = 0; i < this.DW.size(); ++i) {
            this.DW.get(i).setToDefault();
        }
    }

    public void init() {
        this.CW = new CoordinateWindow(this.BH, this.FR, this.mc);
        this.cGui = new CoordsGUI(this, this.mc, this.BH);
        this.Compass = new CompassWindow(this.BH, this.mc);
        this.biomeWindow = new BiomeWindow(this.BH, this.mc, this.CW);
        this.entityWindow = new EntityWindow(this.BH, this.mc, this.CW);
        this.Clock = new ClockWindow(this.BH, this.mc);
        this.arrow = new ArrowCounterWindow(this.BH, this.mc);
        this.FPS = new FPSWindow(this.BH, this.mc, this.CW);
        this.armorWindow = new ArmorWindow(this.BH, this.mc, this.CW);
        this.tipWindow = new TipWindow(this.BH, this.mc, this.CW);
    }

    public void update() {
        if (toggled) {
            for (int i = 0; i < this.DW.size(); ++i) {
                if (this.DW.get(i).getToggled() != 1 && this.showall != 1) continue;
                this.DW.get(i).update();
            }
        }
        if (init) {
            this.cGui.update();
        }
    }

    public void render() {
        if (toggled) {
            for (int i = 0; i < this.DW.size(); ++i) {
                if (this.DW.get(i).getToggled() != 1 && this.showall != 1) continue;
                this.DW.get(i).render();
            }
        }
    }

    static {
        toggled = true;
    }
}
