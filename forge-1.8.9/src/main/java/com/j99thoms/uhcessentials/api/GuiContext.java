package com.j99thoms.uhcessentials.api;

public interface GuiContext {

    int getScreenWidth();
    int getScreenHeight();
    boolean isScreenOpen();

    int getMouseX();
    int getMouseY();
    boolean isMouseButtonDown(int button);

    boolean getEventKeyState();
    Key getEventKey(); // nullable — returns null for unmapped codes
    boolean isKeyDown(Key key);
}
