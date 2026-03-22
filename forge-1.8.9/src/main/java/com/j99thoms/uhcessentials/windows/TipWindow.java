package com.j99thoms.uhcessentials.windows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j99thoms.uhcessentials.BetterHUD;

public class TipWindow extends BaseWindow {

    private static final Logger LOGGER = LogManager.getLogger(TipWindow.class);

    BetterHUD betterHUD;

    private int x = 2;
    private int y = 220;

    private int fillRed = 69;
    private int fillGreen = 69;
    private int fillBlue = 69;
    private int fillAlpha = 150;

    private int borderRed = 0;
    private int borderGreen = 0;
    private int borderBlue = 0;
    private int borderAlpha = 255;

    private int width = 0;
    private int height = 0;

    public static double toggle = 0;

    private float thickness = .8f;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager fileManager;

    private Minecraft mc;
    private CoordinateWindow coordWindow;

    private ArrayList<String> tips;
    public static boolean gotTips = false;
    public Random random = new Random();
    public boolean closeTip = true;
    public int currentTipIndex = 0;
    private String tipOfThePage = "";

    public TipWindow(BetterHUD betterHUD, Minecraft mc, CoordinateWindow coordWindow) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.coordWindow = coordWindow;
        this.tips = new ArrayList<String>();
    }

    @Override
    public String getToolTip() {
        return "Note to user: Don't touch the tip`if you click again I'll show another tip ;)";
    }

    @Override
    public void toggle() {
        if (toggle == 0 && !gotTips) {
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL("https://pastebin.com/raw/EErKqZMx").openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                tips.clear();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    tips.add(inputLine);
                }
                in.close();
                gotTips = true;
            } catch (Exception e) {
                LOGGER.warn("Could not fetch tips: " + e);
            }
        } else {
            newTip();
        }
    }

    @Override
    public void setToDefault() {
        setX(2);
        setY(220);
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(770, 771);

        if (!gotTips) {
            String tipLoad = "Click me to load the tips!";
            width = betterHUD.getFontRenderer().getStringWidth(tipLoad);
            betterHUD.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    coordWindow.getR(), coordWindow.getG(), coordWindow.getB(), coordWindow.getA(),
                    coordWindow.getBorderR(), coordWindow.getBorderG(), coordWindow.getBorderB(), coordWindow.getBorderA(),
                    coordWindow.getThickness());
            betterHUD.drawShadowedFont(tipLoad, x, y, -1);
        } else if (!tipOfThePage.contains("`")) {
            width = betterHUD.getFontRenderer().getStringWidth(tipOfThePage);
            betterHUD.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    coordWindow.getR(), coordWindow.getG(), coordWindow.getB(), coordWindow.getA(),
                    coordWindow.getBorderR(), coordWindow.getBorderG(), coordWindow.getBorderB(), coordWindow.getBorderA(),
                    coordWindow.getThickness());
            betterHUD.drawShadowedFont(tipOfThePage, x, y, -1);
        } else {
            String[] split = tipOfThePage.split("`");
            int longestWidth = 0;
            for (int i = 0; i < split.length; i++) {
                if (betterHUD.getFontRenderer().getStringWidth(split[i]) > longestWidth)
                    longestWidth = betterHUD.getFontRenderer().getStringWidth(split[i]);
            }
            width = longestWidth;
            betterHUD.drawHUDRectWithBorder(x - 1, y - 1, longestWidth + 2, split.length * 10,
                    coordWindow.getR(), coordWindow.getG(), coordWindow.getB(), coordWindow.getA(),
                    coordWindow.getBorderR(), coordWindow.getBorderG(), coordWindow.getBorderB(), coordWindow.getBorderA(),
                    coordWindow.getThickness());
            for (int j = 0; j < split.length; j++) {
                betterHUD.drawShadowedFont(split[j], x, y + j * 10, -1);
            }
        }

        GlStateManager.disableBlend();
    }

    public void newTip() {
        tipOfThePage = tips.get(currentTipIndex);
        currentTipIndex++;
        if (currentTipIndex >= tips.size()) {
            currentTipIndex = 0;
        }
    }

    @Override
    public void render() {
    }

    @Override
    public String getName() {
        return "Tips";
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setRGBA(int red, int green, int blue, int alpha) {
    }

    @Override
    public int getR() {
        return 0;
    }

    @Override
    public int getG() {
        return 0;
    }

    @Override
    public int getB() {
        return 0;
    }

    @Override
    public int getA() {
        return 0;
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
    }

    @Override
    public int getBorderR() {
        return 0;
    }

    @Override
    public int getBorderG() {
        return 0;
    }

    @Override
    public int getBorderB() {
        return 0;
    }

    @Override
    public int getBorderA() {
        return 0;
    }

    @Override
    public void setThickness(float thickness) {
    }

    @Override
    public double getThickness() {
        return 0;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void save() {
    }

    @Override
    public void load() {
    }
}
