package com.j99thoms.uhcessentials.windows;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.j99thoms.uhcessentials.BetterHUD;

public class HUDConfigScreen extends GuiScreen {

    private static boolean[] keyStates;
    private WindowManager windowManager;
    private BetterHUD betterHUD;
    private Minecraft mc;
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
    private boolean moved = false;
    private int drag = 54;
    private int bright = 48;
    private int lastMove;
    private boolean firstDrag = false;
    private boolean on = false;
    private Colorizer color;
    private FileManager gammaFileManager;
    private FileManager keysFileManager;
    private ArrayList<Double> gammaData;
    private ArrayList<Double> keysData;
    private boolean pressed = false;
    private int previewAlpha = 255;
    private int previewR = 255;
    private int previewG = 255;
    private int previewB = 255;
    private boolean alphaDown = true;
    private Random random = new Random();
    public long lastTime;
    public static boolean guiOpen;
    private boolean optionsMenu = false;
    private WindowTheme theme;

    private String resetAll = "Reset All Windows";
    private String toggleButton = "Toggle UHC Essentials";
    private String copyCoordinates = "Copy coordinates to clipboard(or press NumPad7)";
    String options = "Options";

    static {
        guiOpen = false;
    }

    public HUDConfigScreen(WindowManager windowManager, Minecraft mc, BetterHUD betterHUD) {
        this.windowManager = windowManager;
        keyStates = new boolean[256];
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.theme = windowManager.theme;
        gammaFileManager = new FileManager("Gamma", 1);
        keysFileManager = new FileManager("keys.txt", 2);
        keysData = keysFileManager.getArray();
        if (keysData.size() < 2) {
            keysData.add((double) drag);
            keysData.add((double) bright);
            keysFileManager.setArray(keysData);
            keysData.clear();
        } else {
            drag = (int) keysData.get(0).doubleValue();
            bright = (int) keysData.get(1).doubleValue();
        }
        optionMenu = new OptionMenu(mc, this, betterHUD);
        gammaData = gammaFileManager.getArray();
        if (gammaData.size() < 1) {
            gammaData.add((double) mc.gameSettings.gammaSetting);
            gammaFileManager.setArray(gammaData);
            gammaData.clear();
        } else {
            gamma = gammaData.get(0);
            mc.gameSettings.gammaSetting = (float) gamma;
        }
    }

    public void save() {
        gammaData.clear();
        gammaData.add(gamma);
        gammaFileManager.setArray(gammaData);
    }

    public void update() {
        checkKeys();
        if (color != null && on) {
            color.update();
        }
        if (mouseFree && !optionsMenu && !on) {
            ScaledResolution scaledRes = new ScaledResolution(mc);
            betterHUD.drawHUDRectWithBorder(
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(options) / 2,
                    scaledRes.getScaledHeight() / 2 - 5,
                    betterHUD.getFontRenderer().getStringWidth(options) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            betterHUD.drawShadowedFont(options,
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(options) / 2 + 1,
                    scaledRes.getScaledHeight() / 2 - 4, -1);
            betterHUD.drawHUDRectWithBorder(
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(resetAll) / 2,
                    scaledRes.getScaledHeight() / 2 - 5 + 12,
                    betterHUD.getFontRenderer().getStringWidth(resetAll) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            betterHUD.drawShadowedFont(resetAll,
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(resetAll) / 2 + 1,
                    scaledRes.getScaledHeight() / 2 - 4 + 12, -1);
            betterHUD.drawHUDRectWithBorder(
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(toggleButton) / 2,
                    scaledRes.getScaledHeight() / 2 - 5 + 24,
                    betterHUD.getFontRenderer().getStringWidth(toggleButton) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            betterHUD.drawShadowedFont(toggleButton,
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(toggleButton) / 2 + 1,
                    scaledRes.getScaledHeight() / 2 - 4 + 24, -1);
            betterHUD.drawHUDRectWithBorder(
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(copyCoordinates) / 2,
                    scaledRes.getScaledHeight() / 2 - 5 + 36,
                    betterHUD.getFontRenderer().getStringWidth(copyCoordinates) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            betterHUD.drawShadowedFont(copyCoordinates,
                    scaledRes.getScaledWidth() / 2 - betterHUD.getFontRenderer().getStringWidth(copyCoordinates) / 2 + 1,
                    scaledRes.getScaledHeight() / 2 - 4 + 36, -1);
        }
        if (mouseFree && !optionsMenu) {
            windowManager.armorWindow.updateInGUI();
            drag();
            if (windowManager.tipWindow.closeTip && windowManager.tipWindow.gotTips) {
                windowManager.tipWindow.newTip();
                windowManager.tipWindow.closeTip = false;
            }
        } else {
            windowManager.tipWindow.closeTip = true;
        }
        if (optionsMenu) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(optionMenu);
            }
            optionMenu.render();
        }
        if (fullbright) {
            mc.gameSettings.gammaSetting = 2000.0f;
        } else if (shouldChange) {
            mc.gameSettings.gammaSetting = (float) gamma;
            shouldChange = false;
            save();
        } else if (gamma != mc.gameSettings.gammaSetting) {
            gamma = mc.gameSettings.gammaSetting;
            save();
        }
    }

    public static boolean checkKey(int i) {
        if (Keyboard.isKeyDown(i) != keyStates[i]) {
            keyStates[i] = !keyStates[i];
            return keyStates[i];
        }
        return false;
    }

    public void checkKeys() {
        if (Keyboard.getEventKeyState()) {
            keysFileManager = new FileManager("keys.txt", 2);
            keysData = keysFileManager.getArray();
            if (keysData.size() < 2) {
                keysData.add((double) drag);
                keysData.add((double) bright);
                keysFileManager.setArray(keysData);
                keysData.clear();
            } else {
                drag = (int) keysData.get(0).doubleValue();
                bright = (int) keysData.get(1).doubleValue();
            }
        }
        if (mc.currentScreen == null && !optionsMenu) {
            if (checkKey(drag)) {
                guiOpen = true;
                windowManager.showAll();
                mouseFree = true;
                mc.displayGuiScreen(this);
            } else if (checkKey(bright)) {
                fullbright = !fullbright;
                shouldChange = true;
            } else if (checkKey(71)) {
                // NumPad7 — copy coordinates to clipboard
                String myString = "x: " + (int) mc.thePlayer.posX
                        + " y: " + (int) (mc.thePlayer.posY - 1)
                        + " z: " + (int) mc.thePlayer.posZ;
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                clpbrd.setContents(stringSelection, null);
            }
        }
        if (checkKey(1)) {
            // ESC — close config GUI
            guiOpen = false;
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
            color = null;
        }
    }

    public void drag() {
        x = Mouse.getX();
        y = Mouse.getY();
        x = Mouse.getEventX() * this.width / mc.displayWidth;
        y = this.height - Mouse.getEventY() * this.height / mc.displayHeight - 1;
        dx = x - lastX;
        dy = y - lastY;
        lastX = x;
        lastY = y;
        ScaledResolution scaledRes = new ScaledResolution(mc);
        int screenWidth = scaledRes.getScaledWidth();
        int screenHeight = scaledRes.getScaledHeight();
        if (Mouse.isButtonDown(0) && (!pressed || grabbed) && !optionsMenu) {
            if (!Mouse.isButtonDown(3)) {
                if (x >= screenWidth / 2 - betterHUD.getFontRenderer().getStringWidth(options) / 2
                        && x <= screenWidth / 2 + betterHUD.getFontRenderer().getStringWidth(options) / 2
                        && y >= screenHeight / 2 - 22 && y <= screenHeight / 2 + 22) {
                    windowManager.reset();
                }
                if (!firstDrag) {
                    lastMove = 0;
                }
                if (!grabbed) {
                    for (int i = 0; i < windowManager.windows.size(); i++) {
                        if (x >= windowManager.windows.get(i).getX() && x <= windowManager.windows.get(i).getX() + windowManager.windows.get(i).getWidth()) {
                            if (y < windowManager.windows.get(i).getY() || y > windowManager.windows.get(i).getY() + windowManager.windows.get(i).getHeight())
                                continue;
                            draggedWindow = windowManager.windows.get(i);
                            grabbed = true;
                            firstDrag = true;
                            return;
                        }
                        if (x >= screenWidth / 2 - betterHUD.getFontRenderer().getStringWidth(options) / 2
                                && x <= screenWidth / 2 + betterHUD.getFontRenderer().getStringWidth(options) / 2
                                && y >= screenHeight / 2 - 5 && y <= screenHeight / 2 + 5 && !on) {
                            mc.displayGuiScreen(null);
                            optionsMenu = true;
                            return;
                        }
                        if (x >= screenWidth / 2 - betterHUD.getFontRenderer().getStringWidth(resetAll) / 2
                                && x <= screenWidth / 2 + betterHUD.getFontRenderer().getStringWidth(resetAll) / 2
                                && y >= screenHeight / 2 - 5 + 12 && y <= screenHeight / 2 + 5 + 12 && !on) {
                            windowManager.resetAllWindowsPositions();
                            pressed = true;
                            return;
                        }
                        if (x >= screenWidth / 2 - betterHUD.getFontRenderer().getStringWidth(toggleButton) / 2
                                && x <= screenWidth / 2 + betterHUD.getFontRenderer().getStringWidth(toggleButton) / 2
                                && y >= screenHeight / 2 - 5 + 24 && y <= screenHeight / 2 + 5 + 24 && !on) {
                            WindowManager.toggled = !WindowManager.toggled;
                            pressed = true;
                            return;
                        }
                        if (x < screenWidth / 2 - betterHUD.getFontRenderer().getStringWidth(copyCoordinates) / 2
                                || x > screenWidth / 2 + betterHUD.getFontRenderer().getStringWidth(copyCoordinates) / 2
                                || y < screenHeight / 2 - 5 + 36 || y > screenHeight / 2 + 5 + 36 || on)
                            continue;
                        String myString = "x: " + (int) mc.thePlayer.posX
                                + " y: " + (int) (mc.thePlayer.posY - 1)
                                + " z: " + (int) mc.thePlayer.posZ;
                        StringSelection stringSelection = new StringSelection(myString);
                        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clpbrd.setContents(stringSelection, null);
                        pressed = true;
                        return;
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
        if (!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
            pressed = false;
        }
        if (Mouse.isButtonDown(1) && !pressed) {
            pressed = true;
            for (int i = 0; i < windowManager.windows.size(); i++) {
                draggedWindow = windowManager.windows.get(i);
                if (x < windowManager.windows.get(i).getX() || x > windowManager.windows.get(i).getX() + windowManager.windows.get(i).getWidth()
                        || y < windowManager.windows.get(i).getY() || y > windowManager.windows.get(i).getY() + windowManager.windows.get(i).getHeight()
                        || !draggedWindow.isThemed())
                    continue;
                if (!on) {
                    color = new Colorizer(betterHUD, draggedWindow, mc);
                    on = true;
                    break;
                }
                color = null;
                on = false;
            }
        }
        if (!(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || (pressed && !grabbed) || optionsMenu)) {
            for (int i = 0; i < windowManager.windows.size(); i++) {
                if (x < windowManager.windows.get(i).getX() || x > windowManager.windows.get(i).getX() + windowManager.windows.get(i).getWidth()
                        || y < windowManager.windows.get(i).getY() || y > windowManager.windows.get(i).getY() + windowManager.windows.get(i).getHeight())
                    continue;
                BaseWindow hoveredWindow = windowManager.windows.get(i);
                if (previewAlpha > 255) {
                    alphaDown = true;
                } else if (previewAlpha < 0 && hoveredWindow.isThemed()) {
                    alphaDown = false;
                    previewR = random.nextInt(255);
                    previewG = random.nextInt(255);
                    previewB = random.nextInt(255);
                } else if (previewAlpha < 150 && !hoveredWindow.isThemed()) {
                    alphaDown = false;
                }
                int time = 10;
                if (hoveredWindow.isThemed()) {
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
                        if (betterHUD.getFontRenderer().getStringWidth(split[j]) > longestWidth)
                            longestWidth = betterHUD.getFontRenderer().getStringWidth(split[j]);
                    }
                    if (hoveredWindow.isThemed()) {
                        betterHUD.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                previewR, previewG, previewB, previewAlpha,
                                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), previewAlpha,
                                theme.getThickness());
                    } else {
                        betterHUD.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                theme.getR(), theme.getG(), theme.getB(), previewAlpha,
                                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                                theme.getThickness());
                    }
                    for (int j = 0; j < split.length; j++) {
                        betterHUD.drawShadowedFont(split[j], x + 10, y + j * 10, -1);
                    }
                    continue;
                }
                int tipWidth = betterHUD.getFontRenderer().getStringWidth(hoveredWindow.getToolTip());
                betterHUD.drawHUDRectWithBorder(x - 1 + 10, y - 1, tipWidth + 2, 10,
                        theme.getR(), theme.getG(), theme.getB(), previewAlpha,
                        theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                        theme.getThickness());
                betterHUD.drawShadowedFont(hoveredWindow.getToolTip(), x + 10, y, -1);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
