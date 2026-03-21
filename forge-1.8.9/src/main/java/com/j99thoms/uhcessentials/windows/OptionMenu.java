package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.j99thoms.uhcessentials.BetterHUD;

public class OptionMenu extends GuiScreen {

    private Minecraft mc;
    private CoordsGUI cGui;
    private BetterHUD BH;
    private FileManager FM;
    private ArrayList<Double> data = new ArrayList<Double>();

    private double drag;
    private double bright;
    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private int width1;
    private int width2;
    private int height1;
    private int height2;
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
        this.width1 = 40;
        this.width2 = 40;
        this.height1 = 10;
        this.height2 = 10;
        FM = new FileManager("keys.txt", 2);
        data = FM.getArray();
        drag = (int) data.get(0).doubleValue();
        bright = (int) data.get(1).doubleValue();
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
        } else if (set2 && Keyboard.getEventKeyState()) {
            button2 = Keyboard.getEventKey();
            text2 = Keyboard.getKeyName((int) button2);
            set2 = false;
            data.set(1, button2);
            save();
        }
        ScaledResolution var1 = new ScaledResolution(mc);
        x1 = var1.getScaledWidth() / 2 - width1 / 2;
        y1 = var1.getScaledHeight() / 2 - height1 / 2 - 22;
        x2 = var1.getScaledWidth() / 2 - width2 / 2;
        y2 = var1.getScaledHeight() / 2 - height2 / 2 + 22;
        BH.drawHUDRectWithBorder(x1, y1, width1, height1, 0, 0, 0, 255, 255, 255, 255, 255, 1.5);
        mc.fontRendererObj.drawString(func1, x1 - mc.fontRendererObj.getStringWidth(func1) / 4, y1 - 12, -1);
        BH.drawHUDRectWithBorder(x2, y2, width2, height2, 0, 0, 0, 255, 255, 255, 255, 255, 1.5);
        mc.fontRendererObj.drawString(func2, x2 - mc.fontRendererObj.getStringWidth(func2) / 4, y2 - 12, -1);
        if (!set1 && !set2) {
            mc.fontRendererObj.drawString(text1, x1 + 1, y1 + 1, -1);
            mc.fontRendererObj.drawString(text2, x2 + 1, y2 + 1, -1);
        } else if (set1) {
            mc.fontRendererObj.drawString(text1, x1 + 1, y1 + 1, -65536);
            mc.fontRendererObj.drawString(text2, x2 + 1, y2 + 1, -1);
        } else if (set2) {
            mc.fontRendererObj.drawString(text1, x1 + 1, y1 + 1, -1);
            mc.fontRendererObj.drawString(text2, x2 + 1, y2 + 1, -65536);
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
        int x = Mouse.getEventX() * this.width / mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
        if (x >= x1 && x <= x1 + width1 && y >= y1 && y <= y1 + height1) {
            set1 = true;
        }
        if (x >= x2 && x <= x2 + width2 && y >= y2 && y <= y2 + height2) {
            set2 = true;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
