package com.j99thoms.uhcessentials.api;

public interface GuiContext {

    int getScreenWidth();
    int getScreenHeight();
    boolean isScreenOpen();

    int getMouseX();
    int getMouseY();
    boolean isMouseButtonDown(int button);

    boolean getEventKeyState();
    int getEventKey();
    boolean isKeyDown(int key);
    String getKeyName(int keyCode);
}
