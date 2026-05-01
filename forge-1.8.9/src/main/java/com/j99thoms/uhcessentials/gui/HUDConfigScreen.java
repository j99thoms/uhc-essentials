package com.j99thoms.uhcessentials.gui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.EnumMap;
import java.util.Random;

import com.j99thoms.uhcessentials.api.GameContext;
import com.j99thoms.uhcessentials.api.GuiContext;
import com.j99thoms.uhcessentials.api.HUDGraphics;
import com.j99thoms.uhcessentials.api.Key;
import com.j99thoms.uhcessentials.util.FileManager;
import com.j99thoms.uhcessentials.windows.BaseWindow;
import com.j99thoms.uhcessentials.windows.Themeable;
import com.j99thoms.uhcessentials.windows.WindowManager;
import com.j99thoms.uhcessentials.windows.WindowTheme;

public class HUDConfigScreen {

    private static final int BUTTON_PADDING = 2;
    private static final int BUTTON_HEIGHT  = 10;
    private static final int BUTTON_SPACING = 12;

    private final WindowManager windowManager;
    private final HUDGraphics hudGraphics;
    private final GuiContext guiContext;
    private final GameContext gameContext;
    
    private final EnumMap<Key, Boolean> keyStates = new EnumMap<>(Key.class);
    private OptionMenu optionMenu;
    private boolean fullbright = false;
    private boolean shouldChange = false;
    private double gamma;
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private int dx = 0;
    private int dy = 0;
    private boolean mouseFree = false;
    private boolean grabbed = false;
    private BaseWindow draggedWindow;
    private Key dragKey = Key.RIGHT_SHIFT;
    private Key brightKey = Key.B;
    private int lastMove;
    private boolean firstDrag = false;
    private boolean on = false;
    private Colorizer colorizer;
    private FileManager gammaFileManager;
    private FileManager keysFileManager;
    private ArrayList<Double> gammaData;
    private ArrayList<String> keysData;
    private boolean pressed = false;
    private int previewAlpha = 255;
    private int previewR = 255;
    private int previewG = 255;
    private int previewB = 255;
    private boolean alphaDown = true;
    private Random random = new Random();
    public long lastTime;
    private boolean optionsMenu = false;
    private WindowTheme theme;

    private Button optionsButton, resetButton, toggleButton, copyCoordsButton;
    private List<Button> buttons;

    public HUDConfigScreen(WindowManager windowManager, HUDGraphics hudGraphics, GuiContext guiContext, GameContext gameContext) {
        this.windowManager = windowManager;
        this.guiContext = guiContext;
        this.gameContext = gameContext;
        this.hudGraphics = hudGraphics;
        this.theme = windowManager.getTheme();
        gammaFileManager = new FileManager("Gamma", 1);
        keysFileManager = new FileManager("keys.txt", 2);
        keysData = keysFileManager.getStringArray();
        if (keysData.size() < 2) {
            keysData.clear();
            keysData.add(dragKey.name());
            keysData.add(brightKey.name());
            keysFileManager.setStringArray(keysData);
            keysData.clear();
        } else {
            try { dragKey   = Key.valueOf(keysData.get(0)); } catch (Exception e) { dragKey   = Key.RIGHT_SHIFT; }
            try { brightKey = Key.valueOf(keysData.get(1)); } catch (Exception e) { brightKey = Key.B; }
        }
        optionMenu = new OptionMenu(hudGraphics, guiContext);
        gammaData = gammaFileManager.getArray();
        if (gammaData.size() < 1) {
            gammaData.add((double) gameContext.getGamma());
            gammaFileManager.setArray(gammaData);
            gammaData.clear();
        } else {
            gamma = gammaData.get(0);
            gameContext.setGamma((float) gamma);
        }

        resetButton      = getButton("Reset All Windows");
        toggleButton     = getButton("Toggle UHC Essentials");
        copyCoordsButton = getButton("Copy coordinates to clipboard(or press NumPad7)");
        optionsButton    = getButton("Options");
        buttons = Arrays.asList(optionsButton, resetButton, toggleButton, copyCoordsButton);
    }

    private Button getButton(String label) {
        return Button.fromLabel(hudGraphics, label, BUTTON_PADDING, BUTTON_HEIGHT);
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public Colorizer getColorizer() {
        return colorizer;
    }

    public void save() {
        gammaData.clear();
        gammaData.add(gamma);
        gammaFileManager.setArray(gammaData);
    }

    public ScreenRequest render() {
        ScreenRequest request = checkKeys();
        if (request != ScreenRequest.NONE) {
            return request;
        }
        if (colorizer != null && on) {
            colorizer.update();
        }
        if (mouseFree && !optionsMenu && !on) {
            updateButtonPositions();
            buttons.forEach(b -> b.render(0,0,0,255, 255,255,255,255, 0.5));
        }
        if (mouseFree && !optionsMenu) {
            ScreenRequest dragRequest = drag();
            if (dragRequest != ScreenRequest.NONE) {
                return dragRequest;
            }
            if (windowManager.getTipWindow().isClosed() && windowManager.getTipWindow().hasTips()) {
                windowManager.getTipWindow().newTip();
                windowManager.getTipWindow().open();
            }
        } else {
            windowManager.getTipWindow().close();
        }
        if (optionsMenu) {
            optionMenu.render();
        }
        if (fullbright) {
            gameContext.setGamma(2000.0f);
        } else if (shouldChange) {
            gameContext.setGamma((float) gamma);
            shouldChange = false;
            save();
        } else if (gamma != gameContext.getGamma()) {
            gamma = gameContext.getGamma();
            save();
        }
        return ScreenRequest.NONE;
    }

    private void updateButtonPositions() {
        int centerX = guiContext.getScreenWidth()  / 2;
        int centerY = guiContext.getScreenHeight() / 2;

        for (int i = 0; i < buttons.size(); i++) {
            Button button = buttons.get(i);
            button.x = centerX - button.width / 2;
            button.y = centerY - button.height / 2 + i * BUTTON_SPACING;
        }
    }

    private boolean checkKey(Key key) {
        boolean current = guiContext.isKeyDown(key);
        boolean previous = keyStates.getOrDefault(key, false);
        if (current != previous) {
            keyStates.put(key, current);
            return current;
        }
        return false;
    }

    public ScreenRequest checkKeys() {
        if (guiContext.getEventKeyState()) {
            keysFileManager = new FileManager("keys.txt", 2);
            keysData = keysFileManager.getStringArray();
            if (keysData.size() < 2) {
                keysData.add(dragKey.name());
                keysData.add(brightKey.name());
                keysFileManager.setStringArray(keysData);
                keysData.clear();
            } else {
                try { dragKey   = Key.valueOf(keysData.get(0)); } catch (Exception e) { dragKey   = Key.RIGHT_SHIFT; }
                try { brightKey = Key.valueOf(keysData.get(1)); } catch (Exception e) { brightKey = Key.B; }
            }
        }
        if (!guiContext.isScreenOpen() && !optionsMenu) {
            if (checkKey(dragKey)) {
                WindowManager.configScreenOpen = true;
                windowManager.showAll();
                mouseFree = true;
                return ScreenRequest.OPEN_CONFIG;
            } else if (checkKey(brightKey)) {
                fullbright = !fullbright;
                shouldChange = true;
            } else if (checkKey(Key.NUMPAD_7)) {
                // NumPad7 — copy coordinates to clipboard
                String myString = "x: " + (int) gameContext.getPlayerX()
                        + " y: " + (int) Math.floor(gameContext.getPlayerY())
                        + " z: " + (int) gameContext.getPlayerZ();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        }
        if (checkKey(Key.ESCAPE)) {
            // ESC — close config GUI
            WindowManager.configScreenOpen = false;
            optionsMenu = false;
            mouseFree = false;
            optionMenu.reset();
            windowManager.reset();
            on = false;
            lastX = 0;
            lastY = 0;
            dx = 0;
            dy = 0;
            grabbed = false;
            colorizer = null;
        }
        return ScreenRequest.NONE;
    }

    public ScreenRequest drag() {
        x = guiContext.getMouseX();
        y = guiContext.getMouseY();
        dx = x - lastX;
        dy = y - lastY;
        lastX = x;
        lastY = y;
        if (guiContext.isMouseButtonDown(0) && (!pressed || grabbed) && !optionsMenu) {
            if (!guiContext.isMouseButtonDown(3)) {
                if (optionsButton.contains(x, y)) {
                    windowManager.reset();
                }
                if (!firstDrag) {
                    lastMove = 0;
                }
                if (!grabbed) {
                    for (int i = 0; i < windowManager.getWindows().size(); i++) {
                        if (x >= windowManager.getWindows().get(i).getX() && x <= windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()) {
                            if (y < windowManager.getWindows().get(i).getY() || y > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight())
                                continue;
                            draggedWindow = windowManager.getWindows().get(i);
                            grabbed = true;
                            firstDrag = true;
                            return ScreenRequest.NONE;
                        }
                        if (optionsButton.contains(x, y) && !on) {
                            optionsMenu = true;
                            return ScreenRequest.OPEN_OPTIONS;
                        }
                        if (resetButton.contains(x, y) && !on) {
                            windowManager.resetAllWindowsPositions();
                            pressed = true;
                            return ScreenRequest.NONE;
                        }
                        if (toggleButton.contains(x, y) && !on) {
                            WindowManager.setToggled(!WindowManager.isToggled());
                            pressed = true;
                            return ScreenRequest.NONE;
                        }
                        if (!copyCoordsButton.contains(x, y) || on)
                            continue;
                        String myString = "x: " + (int) gameContext.getPlayerX()
                                + " y: " + (int) Math.floor(gameContext.getPlayerY())
                                + " z: " + (int) gameContext.getPlayerZ();
                        StringSelection stringSelection = new StringSelection(myString);
                        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clpbrd.setContents(stringSelection, null);
                        pressed = true;
                        return ScreenRequest.NONE;
                    }
                } else {
                    lastMove += dx + dy;
                    draggedWindow.setX(draggedWindow.getX() + dx);
                    draggedWindow.setY(draggedWindow.getY() + dy);
                    draggedWindow.save();
                }
                pressed = true;
            }
        } else if (lastMove == 0 && firstDrag) {
            firstDrag = false;
            draggedWindow.toggle();
            draggedWindow.save();
        } else {
            firstDrag = false;
            lastX = x;
            lastY = y;
            grabbed = false;
        }
        if (!guiContext.isMouseButtonDown(0) && !guiContext.isMouseButtonDown(1)) {
            pressed = false;
        }
        if (guiContext.isMouseButtonDown(1) && !pressed) {
            pressed = true;
            for (int i = 0; i < windowManager.getWindows().size(); i++) {
                draggedWindow = windowManager.getWindows().get(i);
                if (x < windowManager.getWindows().get(i).getX() || x > windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()
                        || y < windowManager.getWindows().get(i).getY() || y > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight()
                        || !(draggedWindow instanceof Themeable))
                    continue;
                if (!on) {
                    colorizer = new Colorizer(hudGraphics, (Themeable) draggedWindow, guiContext);
                    on = true;
                    return ScreenRequest.OPEN_COLORIZER;
                }
                colorizer = null;
                on = false;
            }
        }
        if (!(guiContext.isMouseButtonDown(0) || guiContext.isMouseButtonDown(1) || (pressed && !grabbed) || optionsMenu)) {
            for (int i = 0; i < windowManager.getWindows().size(); i++) {
                if (x < windowManager.getWindows().get(i).getX() || x > windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()
                        || y < windowManager.getWindows().get(i).getY() || y > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight())
                    continue;
                BaseWindow hoveredWindow = windowManager.getWindows().get(i);
                if (previewAlpha > 255) {
                    alphaDown = true;
                } else if (previewAlpha < 0 && hoveredWindow instanceof Themeable) {
                    alphaDown = false;
                    previewR = random.nextInt(255);
                    previewG = random.nextInt(255);
                    previewB = random.nextInt(255);
                } else if (previewAlpha < 150 && !(hoveredWindow instanceof Themeable)) {
                    alphaDown = false;
                }
                int time = 10;
                if (hoveredWindow instanceof Themeable) {
                    time = 5;
                }
                if (System.currentTimeMillis() - lastTime > time) {
                    previewAlpha = alphaDown ? (previewAlpha -= 2) : (previewAlpha += 2);
                    lastTime = System.currentTimeMillis();
                }
                if (hoveredWindow.getToolTip().contains("`")) {
                    String[] split = hoveredWindow.getToolTip().split("`");
                    int longestWidth = 0;
                    for (int j = 0; j < split.length; j++) {
                        if (hudGraphics.getStringWidth(split[j]) > longestWidth)
                            longestWidth = hudGraphics.getStringWidth(split[j]);
                    }
                    if (hoveredWindow instanceof Themeable) {
                        hudGraphics.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                previewR, previewG, previewB, previewAlpha,
                                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), previewAlpha,
                                theme.getThickness());
                    } else {
                        hudGraphics.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                theme.getR(), theme.getG(), theme.getB(), previewAlpha,
                                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                                theme.getThickness());
                    }
                    for (int j = 0; j < split.length; j++) {
                        hudGraphics.drawShadowedFont(split[j], x + 10, y + j * 10, -1);
                    }
                    continue;
                }
                int tipWidth = hudGraphics.getStringWidth(hoveredWindow.getToolTip());
                hudGraphics.drawHUDRectWithBorder(x - 1 + 10, y - 1, tipWidth + 2, 10,
                        theme.getR(), theme.getG(), theme.getB(), previewAlpha,
                        theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                        theme.getThickness());
                hudGraphics.drawShadowedFont(hoveredWindow.getToolTip(), x + 10, y, -1);
            }
        }
        return ScreenRequest.NONE;
    }
}
