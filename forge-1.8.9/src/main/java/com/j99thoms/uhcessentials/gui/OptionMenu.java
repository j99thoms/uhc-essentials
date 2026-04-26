package com.j99thoms.uhcessentials.gui;

import java.util.ArrayList;

import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.api.Key;
import com.j99thoms.uhcessentials.util.FileManager;

public class OptionMenu {

    private final HUDGraphics hudGraphics;
    private final GuiContext guiContext;
    private FileManager fileManager;
    private ArrayList<String> data = new ArrayList<String>();

    private Key dragKey;
    private Key brightKey;
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
    private Key keyCode1;
    private Key keyCode2;
    private boolean awaitingKey1;
    private boolean awaitingKey2;

    String label1 = "Draggable HUD";
    String label2 = "Full Gamma Bright";

    public OptionMenu(HUDGraphics hudGraphics, GuiContext guiContext) {
        this.hudGraphics = hudGraphics;
        this.guiContext = guiContext;
        this.button1Width = 40;
        this.button2Width = 40;
        this.button1Height = 10;
        this.button2Height = 10;
        fileManager = new FileManager("keys.txt", 2);
        data = fileManager.getStringArray();
        try { dragKey   = Key.valueOf(data.get(0)); } catch (Exception e) { dragKey   = Key.RIGHT_SHIFT; }
        try { brightKey = Key.valueOf(data.get(1)); } catch (Exception e) { brightKey = Key.B; }
        key1Name = dragKey.name();
        key2Name = brightKey.name();
    }

    public void render() {
        if (guiContext.isMouseButtonDown(0) && !awaitingKey1 && !awaitingKey2) {
            mouse();
        } else if (awaitingKey1) {
            if (guiContext.getEventKeyState()) {
                Key key = guiContext.getEventKey();
                if (key != null) {
                    keyCode1 = key;
                    key1Name = keyCode1.name();
                    awaitingKey1 = false;
                    data.set(0, keyCode1.name());
                    save();
                }
            }
        } else if (awaitingKey2 && guiContext.getEventKeyState()) {
            Key key = guiContext.getEventKey();
            if (key != null) {
                keyCode2 = key;
                key2Name = keyCode2.name();
                awaitingKey2 = false;
                data.set(1, keyCode2.name());
                save();
            }
        }
        button1X = guiContext.getScreenWidth() / 2 - button1Width / 2;
        button1Y = guiContext.getScreenHeight() / 2 - button1Height / 2 - 22;
        button2X = guiContext.getScreenWidth() / 2 - button2Width / 2;
        button2Y = guiContext.getScreenHeight() / 2 - button2Height / 2 + 22;
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
        fileManager.setStringArray(data);
    }

    public void mouse() {
        int x = guiContext.getMouseX();
        int y = guiContext.getMouseY();
        if (x >= button1X && x <= button1X + button1Width && y >= button1Y && y <= button1Y + button1Height) {
            awaitingKey1 = true;
        }
        if (x >= button2X && x <= button2X + button2Width && y >= button2Y && y <= button2Y + button2Height) {
            awaitingKey2 = true;
        }
    }
}
