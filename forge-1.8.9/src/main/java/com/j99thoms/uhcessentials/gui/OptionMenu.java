package com.j99thoms.uhcessentials.gui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.j99thoms.uhcessentials.HUDGraphics;
import com.j99thoms.uhcessentials.windows.FileManager;

public class OptionMenu extends GuiScreen {

    private Minecraft mc;
    private HUDGraphics hudGraphics;
    private FileManager fileManager;
    private ArrayList<Double> data = new ArrayList<Double>();

    private double drag;
    private double bright;
    private int button1X;
    private int button2X;
    private int button1Y;
    private int button2Y;
    private int button1Width;
    private int button2Width;
    private int button1Height;
    private int button2Height;
    private String key1Name;
    private String key2Name;
    private double keyCode1;
    private double keyCode2;
    private boolean awaitingKey1;
    private boolean awaitingKey2;

    String label1 = "Draggable HUD";
    String label2 = "Full Gamma Bright";

    public OptionMenu(Minecraft mc, HUDGraphics hudGraphics) {
        this.mc = mc;
        this.hudGraphics = hudGraphics;
        this.button1Width = 40;
        this.button2Width = 40;
        this.button1Height = 10;
        this.button2Height = 10;
        fileManager = new FileManager("keys.txt", 2);
        data = fileManager.getArray();
        drag = (int) data.get(0).doubleValue();
        bright = (int) data.get(1).doubleValue();
        key1Name = Keyboard.getKeyName((int) drag);
        key2Name = Keyboard.getKeyName((int) bright);
    }

    public void render() {
        if (Mouse.isButtonDown(0) && !awaitingKey1 && !awaitingKey2) {
            mouse();
        } else if (awaitingKey1) {
            if (Keyboard.getEventKeyState()) {
                keyCode1 = Keyboard.getEventKey();
                key1Name = Keyboard.getKeyName((int) keyCode1);
                awaitingKey1 = false;
                data.set(0, keyCode1);
                save();
            }
        } else if (awaitingKey2 && Keyboard.getEventKeyState()) {
            keyCode2 = Keyboard.getEventKey();
            key2Name = Keyboard.getKeyName((int) keyCode2);
            awaitingKey2 = false;
            data.set(1, keyCode2);
            save();
        }
        ScaledResolution scaledRes = new ScaledResolution(mc);
        button1X = scaledRes.getScaledWidth() / 2 - button1Width / 2;
        button1Y = scaledRes.getScaledHeight() / 2 - button1Height / 2 - 22;
        button2X = scaledRes.getScaledWidth() / 2 - button2Width / 2;
        button2Y = scaledRes.getScaledHeight() / 2 - button2Height / 2 + 22;
        hudGraphics.drawHUDRectWithBorder(button1X, button1Y, button1Width, button1Height, 0, 0, 0, 255, 255, 255, 255, 255, 1.5);
        hudGraphics.drawFont(label1, button1X - hudGraphics.getStringWidth(label1) / 4, button1Y - 12, -1);
        hudGraphics.drawHUDRectWithBorder(button2X, button2Y, button2Width, button2Height, 0, 0, 0, 255, 255, 255, 255, 255, 1.5);
        hudGraphics.drawFont(label2, button2X - hudGraphics.getStringWidth(label2) / 4, button2Y - 12, -1);
        if (!awaitingKey1 && !awaitingKey2) {
            hudGraphics.drawFont(key1Name, button1X + 1, button1Y + 1, -1);
            hudGraphics.drawFont(key2Name, button2X + 1, button2Y + 1, -1);
        } else if (awaitingKey1) {
            hudGraphics.drawFont(key1Name, button1X + 1, button1Y + 1, -65536);
            hudGraphics.drawFont(key2Name, button2X + 1, button2Y + 1, -1);
        } else if (awaitingKey2) {
            hudGraphics.drawFont(key1Name, button1X + 1, button1Y + 1, -1);
            hudGraphics.drawFont(key2Name, button2X + 1, button2Y + 1, -65536);
        }
    }

    public void reset() {
        awaitingKey1 = false;
        awaitingKey2 = false;
    }

    public void save() {
        fileManager.setArray(data);
    }

    public void mouse() {
        int x = Mouse.getEventX() * this.width / mc.displayWidth;
        int y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
        if (x >= button1X && x <= button1X + button1Width && y >= button1Y && y <= button1Y + button1Height) {
            awaitingKey1 = true;
        }
        if (x >= button2X && x <= button2X + button2Width && y >= button2Y && y <= button2Y + button2Height) {
            awaitingKey2 = true;
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
