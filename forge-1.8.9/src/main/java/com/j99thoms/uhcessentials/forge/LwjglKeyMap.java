package com.j99thoms.uhcessentials.forge;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.j99thoms.uhcessentials.api.Key;

class LwjglKeyMap {

    private static final Map<Key, Integer> KEY_TO_CODE = new EnumMap<>(Key.class);
    private static final Map<Integer, Key> CODE_TO_KEY = new HashMap<>();

    static {
        // Letters
        put(Key.A, Keyboard.KEY_A);
        put(Key.B, Keyboard.KEY_B);
        put(Key.C, Keyboard.KEY_C);
        put(Key.D, Keyboard.KEY_D);
        put(Key.E, Keyboard.KEY_E);
        put(Key.F, Keyboard.KEY_F);
        put(Key.G, Keyboard.KEY_G);
        put(Key.H, Keyboard.KEY_H);
        put(Key.I, Keyboard.KEY_I);
        put(Key.J, Keyboard.KEY_J);
        put(Key.K, Keyboard.KEY_K);
        put(Key.L, Keyboard.KEY_L);
        put(Key.M, Keyboard.KEY_M);
        put(Key.N, Keyboard.KEY_N);
        put(Key.O, Keyboard.KEY_O);
        put(Key.P, Keyboard.KEY_P);
        put(Key.Q, Keyboard.KEY_Q);
        put(Key.R, Keyboard.KEY_R);
        put(Key.S, Keyboard.KEY_S);
        put(Key.T, Keyboard.KEY_T);
        put(Key.U, Keyboard.KEY_U);
        put(Key.V, Keyboard.KEY_V);
        put(Key.W, Keyboard.KEY_W);
        put(Key.X, Keyboard.KEY_X);
        put(Key.Y, Keyboard.KEY_Y);
        put(Key.Z, Keyboard.KEY_Z);

        // Top-row digits
        put(Key.D0, Keyboard.KEY_0);
        put(Key.D1, Keyboard.KEY_1);
        put(Key.D2, Keyboard.KEY_2);
        put(Key.D3, Keyboard.KEY_3);
        put(Key.D4, Keyboard.KEY_4);
        put(Key.D5, Keyboard.KEY_5);
        put(Key.D6, Keyboard.KEY_6);
        put(Key.D7, Keyboard.KEY_7);
        put(Key.D8, Keyboard.KEY_8);
        put(Key.D9, Keyboard.KEY_9);

        // Function keys
        put(Key.F1,  Keyboard.KEY_F1);
        put(Key.F2,  Keyboard.KEY_F2);
        put(Key.F3,  Keyboard.KEY_F3);
        put(Key.F4,  Keyboard.KEY_F4);
        put(Key.F5,  Keyboard.KEY_F5);
        put(Key.F6,  Keyboard.KEY_F6);
        put(Key.F7,  Keyboard.KEY_F7);
        put(Key.F8,  Keyboard.KEY_F8);
        put(Key.F9,  Keyboard.KEY_F9);
        put(Key.F10, Keyboard.KEY_F10);
        put(Key.F11, Keyboard.KEY_F11);
        put(Key.F12, Keyboard.KEY_F12);

        // Modifiers
        put(Key.LEFT_SHIFT,  Keyboard.KEY_LSHIFT);
        put(Key.RIGHT_SHIFT, Keyboard.KEY_RSHIFT);
        put(Key.LEFT_CTRL,   Keyboard.KEY_LCONTROL);
        put(Key.RIGHT_CTRL,  Keyboard.KEY_RCONTROL);
        put(Key.LEFT_ALT,    Keyboard.KEY_LMENU);
        put(Key.RIGHT_ALT,   Keyboard.KEY_RMENU);

        // Navigation
        put(Key.ESCAPE,    Keyboard.KEY_ESCAPE);
        put(Key.TAB,       Keyboard.KEY_TAB);
        put(Key.CAPS_LOCK, Keyboard.KEY_CAPITAL);
        put(Key.SPACE,     Keyboard.KEY_SPACE);
        put(Key.ENTER,     Keyboard.KEY_RETURN);
        put(Key.BACKSPACE, Keyboard.KEY_BACK);
        put(Key.DELETE,    Keyboard.KEY_DELETE);
        put(Key.INSERT,    Keyboard.KEY_INSERT);
        put(Key.HOME,      Keyboard.KEY_HOME);
        put(Key.END,       Keyboard.KEY_END);
        put(Key.PAGE_UP,   Keyboard.KEY_PRIOR);
        put(Key.PAGE_DOWN, Keyboard.KEY_NEXT);

        // Arrows
        put(Key.UP,    Keyboard.KEY_UP);
        put(Key.DOWN,  Keyboard.KEY_DOWN);
        put(Key.LEFT,  Keyboard.KEY_LEFT);
        put(Key.RIGHT, Keyboard.KEY_RIGHT);

        // Numpad
        put(Key.NUMPAD_0,        Keyboard.KEY_NUMPAD0);
        put(Key.NUMPAD_1,        Keyboard.KEY_NUMPAD1);
        put(Key.NUMPAD_2,        Keyboard.KEY_NUMPAD2);
        put(Key.NUMPAD_3,        Keyboard.KEY_NUMPAD3);
        put(Key.NUMPAD_4,        Keyboard.KEY_NUMPAD4);
        put(Key.NUMPAD_5,        Keyboard.KEY_NUMPAD5);
        put(Key.NUMPAD_6,        Keyboard.KEY_NUMPAD6);
        put(Key.NUMPAD_7,        Keyboard.KEY_NUMPAD7);
        put(Key.NUMPAD_8,        Keyboard.KEY_NUMPAD8);
        put(Key.NUMPAD_9,        Keyboard.KEY_NUMPAD9);
        put(Key.NUMPAD_ADD,      Keyboard.KEY_ADD);
        put(Key.NUMPAD_SUBTRACT, Keyboard.KEY_SUBTRACT);
        put(Key.NUMPAD_MULTIPLY, Keyboard.KEY_MULTIPLY);
        put(Key.NUMPAD_DIVIDE,   Keyboard.KEY_DIVIDE);
        put(Key.NUMPAD_DECIMAL,  Keyboard.KEY_DECIMAL);
        put(Key.NUMPAD_ENTER,    Keyboard.KEY_NUMPADENTER);
        put(Key.NUM_LOCK,        Keyboard.KEY_NUMLOCK);

        // Symbols
        put(Key.MINUS,         Keyboard.KEY_MINUS);
        put(Key.EQUALS,        Keyboard.KEY_EQUALS);
        put(Key.LEFT_BRACKET,  Keyboard.KEY_LBRACKET);
        put(Key.RIGHT_BRACKET, Keyboard.KEY_RBRACKET);
        put(Key.BACKSLASH,     Keyboard.KEY_BACKSLASH);
        put(Key.SEMICOLON,     Keyboard.KEY_SEMICOLON);
        put(Key.APOSTROPHE,    Keyboard.KEY_APOSTROPHE);
        put(Key.GRAVE,         Keyboard.KEY_GRAVE);
        put(Key.COMMA,         Keyboard.KEY_COMMA);
        put(Key.PERIOD,        Keyboard.KEY_PERIOD);
        put(Key.SLASH,         Keyboard.KEY_SLASH);
    }

    private static void put(Key key, int code) {
        KEY_TO_CODE.put(key, code);
        CODE_TO_KEY.put(code, key);
    }

    static int toKeyCode(Key key) {
        Integer code = KEY_TO_CODE.get(key);
        if (code == null) throw new IllegalStateException("Unmapped key: " + key);
        return code;
    }

    static Key fromKeyCode(int code) {
        return CODE_TO_KEY.get(code); // null for unmapped codes
    }
}
