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
    private Button button1;
    private Button button2;
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
        fileManager = new FileManager("keys.txt", 2);
        data = fileManager.getStringArray();
        try { dragKey   = Key.valueOf(data.get(0)); } catch (Exception e) { dragKey   = Key.RIGHT_SHIFT; }
        try { brightKey = Key.valueOf(data.get(1)); } catch (Exception e) { brightKey = Key.B; }
        key1Name = dragKey.name();
        key2Name = brightKey.name();
        button1 = Button.fromLabel(hudGraphics, "RIGHT_SHIFT", 2, 10);
        button2 = Button.fromLabel(hudGraphics, "RIGHT_SHIFT", 2, 10);
        button1.setLabel(key1Name, false);
        button2.setLabel(key2Name, false);
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
                    button1.setLabel(key1Name, false);
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
                button2.setLabel(key2Name, false);
                awaitingKey2 = false;
                data.set(1, keyCode2.name());
                save();
            }
        }
        button1.x = guiContext.getScreenWidth() / 2 - button1.width / 2;
        button2.x = guiContext.getScreenWidth() / 2 - button2.width / 2;
        button1.y = guiContext.getScreenHeight() / 2 - button1.height / 2 - 22;
        button2.y = guiContext.getScreenHeight() / 2 - button2.height / 2 + 22;
        button1.render(0, 0, 0, 255, 255, 255, 255, 255, 1.5, awaitingKey1 ? -65536 : -1);
        button2.render(0, 0, 0, 255, 255, 255, 255, 255, 1.5, awaitingKey2 ? -65536 : -1);
        hudGraphics.drawFont(label1, button1.x - hudGraphics.getStringWidth(label1) / 4, button1.y - 12, -1);
        hudGraphics.drawFont(label2, button2.x - hudGraphics.getStringWidth(label2) / 4, button2.y - 12, -1);
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
        if (button1.contains(x, y)) {
            awaitingKey1 = true;
        }
        if (button2.contains(x, y)) {
            awaitingKey2 = true;
        }
    }
}
