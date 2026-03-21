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

public class CoordsGUI extends GuiScreen {

    private static boolean[] keyStates;
    private WindowManager WM;
    private BetterHUD BH;
    private Minecraft mc;
    private OptionMenu om;
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
    private BaseWindow BW;
    private boolean moved = false;
    private int drag = 54;
    private int bright = 48;
    private int lastMove;
    private boolean firstDrag = false;
    private boolean on = false;
    private Colorizer color;
    private FileManager FM;
    private FileManager FM1;
    private ArrayList<Double> data;
    private ArrayList<Double> data1;
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
    public CoordinateWindow cWindow;

    private String resetAll = "Reset All Windows";
    private String toggleButton = "Toggle UHC Essentials";
    private String copyCoordinates = "Copy coordinates to clipboard(or press NumPad7)";
    String options = "Options";

    static {
        guiOpen = false;
    }

    public CoordsGUI(WindowManager WM, Minecraft mc, BetterHUD BH) {
        this.WM = WM;
        keyStates = new boolean[256];
        this.mc = mc;
        this.BH = BH;
        this.cWindow = WM.CW;
        FM = new FileManager("Gamma", 1);
        FM1 = new FileManager("keys.txt", 2);
        data1 = FM1.getArray();
        if (data1.size() < 2) {
            data1.add((double) drag);
            data1.add((double) bright);
            FM1.setArray(data1);
            data1.clear();
        } else {
            drag = (int) data1.get(0).doubleValue();
            bright = (int) data1.get(1).doubleValue();
        }
        om = new OptionMenu(mc, this, BH);
        data = FM.getArray();
        if (data.size() < 1) {
            data.add((double) mc.gameSettings.gammaSetting);
            FM.setArray(data);
            data.clear();
        } else {
            gamma = data.get(0);
            mc.gameSettings.gammaSetting = (float) gamma;
        }
    }

    public void save() {
        data.clear();
        data.add(gamma);
        FM.setArray(data);
    }

    public void update() {
        checkKeys();
        if (color != null && on) {
            color.update();
        }
        if (mouseFree && !optionsMenu && !on) {
            ScaledResolution var1 = new ScaledResolution(mc);
            BH.drawHUDRectWithBorder(
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(options) / 2,
                    var1.getScaledHeight() / 2 - 5,
                    BH.getFontRenderer().getStringWidth(options) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            BH.drawShadowedFont(options,
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(options) / 2 + 1,
                    var1.getScaledHeight() / 2 - 4, -1);
            BH.drawHUDRectWithBorder(
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(resetAll) / 2,
                    var1.getScaledHeight() / 2 - 5 + 12,
                    BH.getFontRenderer().getStringWidth(resetAll) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            BH.drawShadowedFont(resetAll,
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(resetAll) / 2 + 1,
                    var1.getScaledHeight() / 2 - 4 + 12, -1);
            BH.drawHUDRectWithBorder(
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(toggleButton) / 2,
                    var1.getScaledHeight() / 2 - 5 + 24,
                    BH.getFontRenderer().getStringWidth(toggleButton) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            BH.drawShadowedFont(toggleButton,
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(toggleButton) / 2 + 1,
                    var1.getScaledHeight() / 2 - 4 + 24, -1);
            BH.drawHUDRectWithBorder(
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(copyCoordinates) / 2,
                    var1.getScaledHeight() / 2 - 5 + 36,
                    BH.getFontRenderer().getStringWidth(copyCoordinates) + 1, 10, 0, 0, 0, 255, 255, 255, 255, 255, 0.5);
            BH.drawShadowedFont(copyCoordinates,
                    var1.getScaledWidth() / 2 - BH.getFontRenderer().getStringWidth(copyCoordinates) / 2 + 1,
                    var1.getScaledHeight() / 2 - 4 + 36, -1);
        }
        if (mouseFree && !optionsMenu) {
            WM.armorWindow.updateInGUI();
            drag();
            if (WM.tipWindow.closeTip && TipWindow.gotTips) {
                WM.tipWindow.newTip();
                WM.tipWindow.closeTip = false;
            }
        } else {
            WM.tipWindow.closeTip = true;
        }
        if (optionsMenu) {
            if (mc.currentScreen == null) {
                mc.displayGuiScreen(om);
            }
            om.render();
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
            FM1 = new FileManager("keys.txt", 2);
            data1 = FM1.getArray();
            if (data1.size() < 2) {
                data1.add((double) drag);
                data1.add((double) bright);
                FM1.setArray(data1);
                data1.clear();
            } else {
                drag = (int) data1.get(0).doubleValue();
                bright = (int) data1.get(1).doubleValue();
            }
        }
        if (mc.currentScreen == null && !optionsMenu) {
            if (checkKey(drag)) {
                guiOpen = true;
                WM.showAll();
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
            om.reset();
            WM.reset();
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
        ScaledResolution var1 = new ScaledResolution(mc);
        int Twidth = var1.getScaledWidth();
        int Theight = var1.getScaledHeight();
        if (Mouse.isButtonDown(0) && (!pressed || grabbed) && !optionsMenu) {
            if (!Mouse.isButtonDown(3)) {
                if (x >= Twidth / 2 - BH.getFontRenderer().getStringWidth(options) / 2
                        && x <= Twidth / 2 + BH.getFontRenderer().getStringWidth(options) / 2
                        && y >= Theight / 2 - 22 && y <= Theight / 2 + 22) {
                    WM.reset();
                }
                if (!firstDrag) {
                    lastMove = 0;
                }
                if (!grabbed) {
                    for (int i = 0; i < WM.BW.size(); i++) {
                        if (x >= WM.BW.get(i).getX() && x <= WM.BW.get(i).getX() + WM.BW.get(i).getWidth()) {
                            if (y < WM.BW.get(i).getY() || y > WM.BW.get(i).getY() + WM.BW.get(i).getHeight())
                                continue;
                            BW = WM.BW.get(i);
                            grabbed = true;
                            firstDrag = true;
                            return;
                        }
                        if (x >= Twidth / 2 - BH.getFontRenderer().getStringWidth(options) / 2
                                && x <= Twidth / 2 + BH.getFontRenderer().getStringWidth(options) / 2
                                && y >= Theight / 2 - 5 && y <= Theight / 2 + 5 && !on) {
                            mc.displayGuiScreen(null);
                            optionsMenu = true;
                            return;
                        }
                        if (x >= Twidth / 2 - BH.getFontRenderer().getStringWidth(resetAll) / 2
                                && x <= Twidth / 2 + BH.getFontRenderer().getStringWidth(resetAll) / 2
                                && y >= Theight / 2 - 5 + 12 && y <= Theight / 2 + 5 + 12 && !on) {
                            WM.resetAllWindowsPositions();
                            pressed = true;
                            return;
                        }
                        if (x >= Twidth / 2 - BH.getFontRenderer().getStringWidth(toggleButton) / 2
                                && x <= Twidth / 2 + BH.getFontRenderer().getStringWidth(toggleButton) / 2
                                && y >= Theight / 2 - 5 + 24 && y <= Theight / 2 + 5 + 24 && !on) {
                            WindowManager.toggled = !WindowManager.toggled;
                            pressed = true;
                            return;
                        }
                        if (x < Twidth / 2 - BH.getFontRenderer().getStringWidth(copyCoordinates) / 2
                                || x > Twidth / 2 + BH.getFontRenderer().getStringWidth(copyCoordinates) / 2
                                || y < Theight / 2 - 5 + 36 || y > Theight / 2 + 5 + 36 || on)
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
                    BW.setX(BW.getX() + dx);
                    BW.setY(BW.getY() + dy);
                    BW.save();
                }
                pressed = true;
            }
        } else if (lastMove == 0 && firstDrag) {
            firstDrag = false;
            BW.toggle();
            BW.save();
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
            for (int i = 0; i < WM.BW.size(); i++) {
                BW = WM.BW.get(i);
                if (x < WM.BW.get(i).getX() || x > WM.BW.get(i).getX() + WM.BW.get(i).getWidth()
                        || y < WM.BW.get(i).getY() || y > WM.BW.get(i).getY() + WM.BW.get(i).getHeight()
                        || !BW.getName().equalsIgnoreCase("Coordinate"))
                    continue;
                if (!on) {
                    color = new Colorizer(BH, BW, mc);
                    on = true;
                    break;
                }
                color = null;
                on = false;
            }
        }
        if (!(Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || (pressed && !grabbed) || optionsMenu)) {
            for (int i = 0; i < WM.BW.size(); i++) {
                if (x < WM.BW.get(i).getX() || x > WM.BW.get(i).getX() + WM.BW.get(i).getWidth()
                        || y < WM.BW.get(i).getY() || y > WM.BW.get(i).getY() + WM.BW.get(i).getHeight())
                    continue;
                BaseWindow DW = WM.BW.get(i);
                if (previewAlpha > 255) {
                    alphaDown = true;
                } else if (previewAlpha < 0 && DW.getName() == "Coordinate") {
                    alphaDown = false;
                    previewR = random.nextInt(255);
                    previewG = random.nextInt(255);
                    previewB = random.nextInt(255);
                } else if (previewAlpha < 150 && DW.getName() != "Coordinate") {
                    alphaDown = false;
                }
                int time = 10;
                if (DW.getName() == "Coordinate") {
                    time = 5;
                }
                if (System.currentTimeMillis() - lastTime > time) {
                    previewAlpha = alphaDown ? (previewAlpha -= 2) : (previewAlpha += 2);
                    lastTime = System.currentTimeMillis();
                }
                if (DW.getToolTip().contains("`")) {
                    String[] split = DW.getToolTip().split("`");
                    int longestWidth = 0;
                    for (int j = 0; j < split.length; j++) {
                        if (BH.getFontRenderer().getStringWidth(split[j]) > longestWidth)
                            longestWidth = BH.getFontRenderer().getStringWidth(split[j]);
                    }
                    if (DW.getName() == "Coordinate") {
                        BH.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                previewR, previewG, previewB, previewAlpha,
                                cWindow.getBorderR(), cWindow.getBorderG(), cWindow.getBorderB(), previewAlpha,
                                cWindow.getThickness());
                    } else {
                        BH.drawHUDRectWithBorder(x - 1 + 10, y - 1, longestWidth + 2, split.length * 10,
                                cWindow.getR(), cWindow.getG(), cWindow.getB(), previewAlpha,
                                cWindow.getBorderR(), cWindow.getBorderG(), cWindow.getBorderB(), cWindow.getBorderA(),
                                cWindow.getThickness());
                    }
                    for (int j = 0; j < split.length; j++) {
                        BH.drawShadowedFont(split[j], x + 10, y + j * 10, -1);
                    }
                    continue;
                }
                int tipWidth = BH.getFontRenderer().getStringWidth(DW.getToolTip());
                BH.drawHUDRectWithBorder(x - 1 + 10, y - 1, tipWidth + 2, 10,
                        cWindow.getR(), cWindow.getG(), cWindow.getB(), previewAlpha,
                        cWindow.getBorderR(), cWindow.getBorderG(), cWindow.getBorderB(), cWindow.getBorderA(),
                        cWindow.getThickness());
                BH.drawShadowedFont(DW.getToolTip(), x + 10, y, -1);
            }
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
