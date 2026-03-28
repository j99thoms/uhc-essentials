package com.j99thoms.uhcessentials.windows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.j99thoms.uhcessentials.HUDGraphics;

public class TipWindow extends ThemedWindow {

    private static final Logger LOGGER = LogManager.getLogger(TipWindow.class);

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 220;

    private int x = DEFAULT_X;
    private int y = DEFAULT_Y;

    private int width = 0;
    private int height = 0;

    private double toggle = 0;

    private final Minecraft mc;

    private ArrayList<String> tips;
    boolean gotTips = false;
    boolean closeTip = true;
    private int currentTipIndex = 0;
    private String tipOfThePage = "";

    public TipWindow(HUDGraphics hudGraphics, Minecraft mc, WindowTheme theme) {
        super(hudGraphics, theme);
        this.mc = mc;
        this.tips = new ArrayList<String>();
    }

    @Override
    public String getToolTip() {
        return "Note to user: Don't touch the tip`Left click to cycle tips, right click to change colors";
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
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
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
            width = hudGraphics.getStringWidth(tipLoad);
            hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                    theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                    theme.getThickness());
            hudGraphics.drawShadowedFont(tipLoad, x, y, -1);
        } else if (!tipOfThePage.contains("`")) {
            width = hudGraphics.getStringWidth(tipOfThePage);
            hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                    theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                    theme.getThickness());
            hudGraphics.drawShadowedFont(tipOfThePage, x, y, -1);
        } else {
            String[] split = tipOfThePage.split("`");
            int longestWidth = 0;
            for (int i = 0; i < split.length; i++) {
                if (hudGraphics.getStringWidth(split[i]) > longestWidth)
                    longestWidth = hudGraphics.getStringWidth(split[i]);
            }
            width = longestWidth;
            hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, longestWidth + 2, split.length * 10,
                    theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                    theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                    theme.getThickness());
            for (int j = 0; j < split.length; j++) {
                hudGraphics.drawShadowedFont(split[j], x, y + j * 10, -1);
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return 8;
    }

    @Override
    public void save() {
        theme.save();
    }

    @Override
    public void load() {
    }
}
