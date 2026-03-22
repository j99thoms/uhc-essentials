package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

import com.j99thoms.uhcessentials.BetterHUD;

public class BiomeWindow extends BaseWindow {

    BetterHUD betterHUD;

    private int x = 2;
    private int y = 46;

    private int width = 0;
    private int height = 12;

    private double toggle = 1;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager fileManager;
    private WindowTheme theme;

    private Minecraft mc;

    public BiomeWindow(BetterHUD betterHUD, Minecraft mc, WindowTheme theme) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.theme = theme;
        fileManager = new FileManager("BiomeWindow", 3);
        load();
    }

    @Override
    public boolean isThemed() {
        return true;
    }

    @Override
    public String getToolTip() {
        return "Shows what biome you're currently in`Right click to change colors";
    }

    @Override
    public void setToDefault() {
        setX(2);
        setY(46);
        save();
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        int playerX = (int) Math.floor(mc.thePlayer.posX);
        int playerZ = (int) Math.floor(mc.thePlayer.posZ);
        String biomeName = mc.theWorld.getBiomeGenForCoords(
                new BlockPos(playerX, 64, playerZ)).biomeName;

        width = mc.fontRendererObj.getStringWidth(biomeName);
        betterHUD.drawHUDRectWithBorder(x - 1, y - 1, mc.fontRendererObj.getStringWidth(biomeName) + 1, getHeight(),
                theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                theme.getThickness());
        mc.fontRendererObj.drawStringWithShadow(biomeName, x, y, 0xffffffff);

        if ((int) toggle == 0)
            mc.fontRendererObj.drawStringWithShadow("X", getX() - 5, getY() - 5, 0xffffffff);
    }

    @Override
    public String getName() {
        return "BiomeWindow";
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
    public void setRGBA(int red, int green, int blue, int alpha) {
        theme.setRGBA(red, green, blue, alpha);
    }

    @Override
    public int getR() {
        return theme.getR();
    }

    @Override
    public int getG() {
        return theme.getG();
    }

    @Override
    public int getB() {
        return theme.getB();
    }

    @Override
    public int getA() {
        return theme.getA();
    }

    @Override
    public void setBorderRGB(int red, int green, int blue, int alpha) {
        theme.setBorderRGB(red, green, blue, alpha);
    }

    @Override
    public int getBorderR() {
        return theme.getBorderR();
    }

    @Override
    public int getBorderG() {
        return theme.getBorderG();
    }

    @Override
    public int getBorderB() {
        return theme.getBorderB();
    }

    @Override
    public int getBorderA() {
        return theme.getBorderA();
    }

    @Override
    public void setThickness(float thickness) {
        theme.setThickness(thickness);
    }

    @Override
    public double getThickness() {
        return theme.getThickness();
    }

    @Override
    public int getWidth() {
        return width + 1;
    }

    @Override
    public int getHeight() {
        return 10;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) toggle);
        fileManager.setArray(data);
        theme.save();
    }

    @Override
    public void load() {
        data.clear();
        data = fileManager.getArray();
        if (data.size() < 3) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            toggle = data.get(2);
        }
    }
}
