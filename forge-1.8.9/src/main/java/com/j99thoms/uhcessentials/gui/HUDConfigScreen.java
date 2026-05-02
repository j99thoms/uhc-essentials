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

    private static final int BUTTON_PADDING                  = 2;
    private static final int BUTTON_HEIGHT                   = 10;
    private static final int BUTTON_SPACING                  = 12;
    private static final int TOOLTIP_ANIM_INTERVAL_MS        = 10;
    private static final int THEMED_TOOLTIP_ANIM_INTERVAL_MS = 5;
    private static final int TOOLTIP_ALPHA_STEP              = 2;
    private static final int TOOLTIP_ALPHA_MAX               = 255;
    private static final int TOOLTIP_ALPHA_MIN_PLAIN         = 150;
    private static final int TOOLTIP_ALPHA_MIN_THEMED        = 0;
    private static final float FULLBRIGHT_GAMMA              = 2000.0f;

    private final WindowManager windowManager;
    private final HUDGraphics hudGraphics;
    private final GuiContext guiContext;
    private final GameContext gameContext;
    
    private final EnumMap<Key, Boolean> keyStates = new EnumMap<>(Key.class);
    private OptionMenu optionMenu;
    private boolean isFullbright = false;
    private boolean pendingGammaRestore = false;
    private double gamma;
    private int lastX;
    private int lastY;
    private boolean configModeActive = false;
    private boolean isDraggingWindow = false;
    private BaseWindow draggedWindow;
    private Key dragKey = Key.RIGHT_SHIFT;
    private Key fullbrightKey = Key.B;
    private int totalDragDelta;
    private boolean dragJustStarted = false;
    private boolean colorizerOpen = false;
    private Colorizer colorizer;
    private final FileManager gammaFileManager;
    private final FileManager keysFileManager;
    private ArrayList<Double> gammaData;
    private ArrayList<String> keysData;
    private boolean mouseWasDown = false;
    private int tooltipAlpha = 255;
    private int tooltipColorR = 255;
    private int tooltipColorG = 255;
    private int tooltipColorB = 255;
    private boolean tooltipAlphaFading = true;
    private Random tooltipRandom = new Random();
    private long lastTooltipAnimTime;
    private boolean optionsMenuOpen = false;
    private WindowTheme theme;

    private Button optionsButton, resetButton, toggleButton, copyCoordsButton;
    private List<Button> buttons;

    public HUDConfigScreen(WindowManager windowManager, HUDGraphics hudGraphics, GuiContext guiContext, GameContext gameContext) {
        this.windowManager = windowManager;
        this.guiContext = guiContext;
        this.gameContext = gameContext;
        this.hudGraphics = hudGraphics;
        this.theme = windowManager.getTheme();
        this.gammaFileManager = new FileManager("Gamma", 1);
        this.keysFileManager = new FileManager("keys.txt", 2);
        loadGamma();
        loadKeys();
        optionMenu = new OptionMenu(hudGraphics, guiContext);
        resetButton      = getButton("Reset All Windows");
        toggleButton     = getButton("Toggle UHC Essentials");
        copyCoordsButton = getButton("Copy coordinates to clipboard(or press NumPad7)");
        optionsButton    = getButton("Options");
        buttons = Arrays.asList(optionsButton, resetButton, toggleButton, copyCoordsButton);
    }

    public OptionMenu getOptionMenu() {
        return optionMenu;
    }

    public Colorizer getColorizer() {
        return colorizer;
    }

    private void loadGamma() {
        gammaData = gammaFileManager.getArray();
        if (gammaData.size() < 1) {
            gammaData.add((double) gameContext.getGamma());
            gammaFileManager.setArray(gammaData);
            gammaData.clear();
        } else {
            gamma = gammaData.get(0);
            gameContext.setGamma((float) gamma);
        }
    }

    private void saveGamma() {
        gammaData.clear();
        gammaData.add(gamma);
        gammaFileManager.setArray(gammaData);
    }

    private void loadKeys() {
        keysData = keysFileManager.getStringArray();
        if (keysData.size() < 2) {
            keysData.clear();
            keysData.add(dragKey.name());
            keysData.add(fullbrightKey.name());
            keysFileManager.setStringArray(keysData);
            keysData.clear();
        } else {
            try { dragKey   = Key.valueOf(keysData.get(0)); } catch (Exception e) { dragKey   = Key.RIGHT_SHIFT; }
            try { fullbrightKey = Key.valueOf(keysData.get(1)); } catch (Exception e) { fullbrightKey = Key.B; }
        }
    }

    private Button getButton(String label) {
        return Button.fromLabel(hudGraphics, label, BUTTON_PADDING, BUTTON_HEIGHT);
    }

    public ScreenRequest render() {
        ScreenRequest request = checkKeys();
        if (request != ScreenRequest.NONE) {
            return request;
        }
        if (colorizer != null && colorizerOpen) {
            colorizer.update();
        }
        if (configModeActive && !optionsMenuOpen && !colorizerOpen) {
            updateButtonPositions();
            buttons.forEach(b -> b.render(0,0,0,255, 255,255,255,255, 0.5));
        }
        if (configModeActive && !optionsMenuOpen) {
            ScreenRequest dragRequest = handleMouseInput();
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
        if (optionsMenuOpen) {
            optionMenu.render();
        }
        if (isFullbright) {
            gameContext.setGamma(FULLBRIGHT_GAMMA);
        } else if (pendingGammaRestore) {
            gameContext.setGamma((float) gamma);
            pendingGammaRestore = false;
            saveGamma();
        } else if (gamma != gameContext.getGamma()) {
            gamma = gameContext.getGamma();
            saveGamma();
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

    private ScreenRequest checkKeys() {
        if (guiContext.getEventKeyState()) {
            loadKeys();
        }
        if (!guiContext.isScreenOpen() && !optionsMenuOpen) {
            if (checkKey(dragKey)) {
                WindowManager.configScreenOpen = true;
                windowManager.showAll();
                configModeActive = true;
                return ScreenRequest.OPEN_CONFIG;
            } else if (checkKey(fullbrightKey)) {
                isFullbright = !isFullbright;
                pendingGammaRestore = true;
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
            optionsMenuOpen = false;
            configModeActive = false;
            optionMenu.reset();
            windowManager.reset();
            colorizerOpen = false;
            lastX = 0;
            lastY = 0;
            isDraggingWindow = false;
            colorizer = null;
        }
        return ScreenRequest.NONE;
    }

    private ScreenRequest handleMouseInput() {
        int x  = guiContext.getMouseX();
        int y  = guiContext.getMouseY();
        int dx = x - lastX;
        int dy = y - lastY;
        lastX = x;
        lastY = y;
        ScreenRequest leftClickRequest = handleLeftMouseButton(x, y, dx, dy);
        if (leftClickRequest != ScreenRequest.NONE) return leftClickRequest;
        if (!guiContext.isMouseButtonDown(0) && !guiContext.isMouseButtonDown(1)) {
            mouseWasDown = false;
        }
        ScreenRequest rightClickRequest = handleRightMouseButton(x, y);
        if (rightClickRequest != ScreenRequest.NONE) return rightClickRequest;
        renderHoverTooltips(x, y);
        return ScreenRequest.NONE;
    }

    private ScreenRequest handleLeftMouseButton(int mouseX, int mouseY, int dx, int dy) {
        if (guiContext.isMouseButtonDown(0) && (!mouseWasDown || isDraggingWindow) && !optionsMenuOpen) {
            if (!guiContext.isMouseButtonDown(3)) {
                if (optionsButton.contains(mouseX, mouseY)) {
                    windowManager.reset();
                }
                if (!dragJustStarted) {
                    totalDragDelta = 0;
                }
                if (!isDraggingWindow) {
                    for (int i = 0; i < windowManager.getWindows().size(); i++) {
                        if (mouseX >= windowManager.getWindows().get(i).getX() && mouseX <= windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()) {
                            if (mouseY < windowManager.getWindows().get(i).getY() || mouseY > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight())
                                continue;
                            draggedWindow = windowManager.getWindows().get(i);
                            isDraggingWindow = true;
                            dragJustStarted = true;
                            return ScreenRequest.NONE;
                        }
                        if (optionsButton.contains(mouseX, mouseY) && !colorizerOpen) {
                            optionsMenuOpen = true;
                            return ScreenRequest.OPEN_OPTIONS;
                        }
                        if (resetButton.contains(mouseX, mouseY) && !colorizerOpen) {
                            windowManager.resetAllWindowsPositions();
                            mouseWasDown = true;
                            return ScreenRequest.NONE;
                        }
                        if (toggleButton.contains(mouseX, mouseY) && !colorizerOpen) {
                            WindowManager.setToggled(!WindowManager.isToggled());
                            mouseWasDown = true;
                            return ScreenRequest.NONE;
                        }
                        if (!copyCoordsButton.contains(mouseX, mouseY) || colorizerOpen)
                            continue;
                        String myString = "x: " + (int) gameContext.getPlayerX()
                                + " y: " + (int) Math.floor(gameContext.getPlayerY())
                                + " z: " + (int) gameContext.getPlayerZ();
                        StringSelection stringSelection = new StringSelection(myString);
                        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clpbrd.setContents(stringSelection, null);
                        mouseWasDown = true;
                        return ScreenRequest.NONE;
                    }
                } else {
                    totalDragDelta += dx + dy;
                    draggedWindow.setX(draggedWindow.getX() + dx);
                    draggedWindow.setY(draggedWindow.getY() + dy);
                    draggedWindow.save();
                }
                mouseWasDown = true;
            }
        } else if (totalDragDelta == 0 && dragJustStarted) {
            dragJustStarted = false;
            draggedWindow.toggle();
            draggedWindow.save();
        } else {
            dragJustStarted = false;
            lastX = mouseX;
            lastY = mouseY;
            isDraggingWindow = false;
        }
        return ScreenRequest.NONE;
    }

    private ScreenRequest handleRightMouseButton(int mouseX, int mouseY) {
        if (!guiContext.isMouseButtonDown(1) || mouseWasDown) {
            return ScreenRequest.NONE;
        }
        mouseWasDown = true;
        for (int i = 0; i < windowManager.getWindows().size(); i++) {
            draggedWindow = windowManager.getWindows().get(i);
            if (mouseX < windowManager.getWindows().get(i).getX() || mouseX > windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()
                    || mouseY < windowManager.getWindows().get(i).getY() || mouseY > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight()
                    || !(draggedWindow instanceof Themeable))
                continue;
            if (!colorizerOpen) {
                colorizer = new Colorizer(hudGraphics, (Themeable) draggedWindow, guiContext);
                colorizerOpen = true;
                return ScreenRequest.OPEN_COLORIZER;
            }
            colorizer = null;
            colorizerOpen = false;
        }
        return ScreenRequest.NONE;
    }

    private void renderHoverTooltips(int mouseX, int mouseY) {
        if (guiContext.isMouseButtonDown(0) || guiContext.isMouseButtonDown(1) || (mouseWasDown && !isDraggingWindow) || optionsMenuOpen) {
            return;
        }
        for (int i = 0; i < windowManager.getWindows().size(); i++) {
            if (mouseX < windowManager.getWindows().get(i).getX() || mouseX > windowManager.getWindows().get(i).getX() + windowManager.getWindows().get(i).getWidth()
                    || mouseY < windowManager.getWindows().get(i).getY() || mouseY > windowManager.getWindows().get(i).getY() + windowManager.getWindows().get(i).getHeight())
                continue;
            BaseWindow hoveredWindow = windowManager.getWindows().get(i);
            if (tooltipAlpha > TOOLTIP_ALPHA_MAX) {
                tooltipAlphaFading = true;
            } else if (tooltipAlpha < TOOLTIP_ALPHA_MIN_THEMED && hoveredWindow instanceof Themeable) {
                tooltipAlphaFading = false;
                tooltipColorR = tooltipRandom.nextInt(255);
                tooltipColorG = tooltipRandom.nextInt(255);
                tooltipColorB = tooltipRandom.nextInt(255);
            } else if (tooltipAlpha < TOOLTIP_ALPHA_MIN_PLAIN && !(hoveredWindow instanceof Themeable)) {
                tooltipAlphaFading = false;
            }
            int animInterval = hoveredWindow instanceof Themeable
                    ? THEMED_TOOLTIP_ANIM_INTERVAL_MS
                    : TOOLTIP_ANIM_INTERVAL_MS;
            if (System.currentTimeMillis() - lastTooltipAnimTime > animInterval) {
                tooltipAlpha = tooltipAlphaFading ? (tooltipAlpha -= TOOLTIP_ALPHA_STEP) : (tooltipAlpha += TOOLTIP_ALPHA_STEP);
                lastTooltipAnimTime = System.currentTimeMillis();
            }
            if (hoveredWindow.getToolTip().contains("`")) {
                String[] split = hoveredWindow.getToolTip().split("`");
                int longestWidth = 0;
                for (int j = 0; j < split.length; j++) {
                    if (hudGraphics.getStringWidth(split[j]) > longestWidth)
                        longestWidth = hudGraphics.getStringWidth(split[j]);
                }
                if (hoveredWindow instanceof Themeable) {
                    hudGraphics.drawHUDRectWithBorder(mouseX - 1 + 10, mouseY - 1, longestWidth + 2, split.length * 10,
                            tooltipColorR, tooltipColorG, tooltipColorB, tooltipAlpha,
                            theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), tooltipAlpha,
                            theme.getThickness());
                } else {
                    hudGraphics.drawHUDRectWithBorder(mouseX - 1 + 10, mouseY - 1, longestWidth + 2, split.length * 10,
                            theme.getR(), theme.getG(), theme.getB(), tooltipAlpha,
                            theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                            theme.getThickness());
                }
                for (int j = 0; j < split.length; j++) {
                    hudGraphics.drawShadowedFont(split[j], mouseX + 10, mouseY + j * 10, -1);
                }
                continue;
            }
            int tipWidth = hudGraphics.getStringWidth(hoveredWindow.getToolTip());
            hudGraphics.drawHUDRectWithBorder(mouseX - 1 + 10, mouseY - 1, tipWidth + 2, 10,
                    theme.getR(), theme.getG(), theme.getB(), tooltipAlpha,
                    theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                    theme.getThickness());
            hudGraphics.drawShadowedFont(hoveredWindow.getToolTip(), mouseX + 10, mouseY, -1);
        }
    }
}
