package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;

import com.j99thoms.uhcessentials.BetterHUD;

public class ArmorWindow extends BaseWindow {

    BetterHUD betterHUD;

    private int x = 2;
    private int y = 102;

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

    private ArrayList<Item> armor = new ArrayList<Item>();
    private ArrayList<Float> armorDamage = new ArrayList<Float>();

    public static double toggle = 1;

    private float thickness = .8f;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager fileManager;

    private Minecraft mc;
    private CoordinateWindow coordWindow;

    public ArmorWindow(BetterHUD betterHUD, Minecraft mc, CoordinateWindow coordWindow) {
        this.mc = mc;
        this.betterHUD = betterHUD;
        this.coordWindow = coordWindow;
        fileManager = new FileManager("Armor", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how damaged your armor is";
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : 0;
    }

    @Override
    public void setToDefault() {
        setX(2);
        setY(102);
        save();
    }

    @Override
    public int getToggled() {
        return (int) toggle;
    }

    @Override
    public void update() {
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);

        width = 14;
        height = 0;
        armor.clear();
        armorDamage.clear();

        for (int i = 0; i < mc.thePlayer.inventory.armorInventory.length; i++) {
            int armorIdx = mc.thePlayer.inventory.armorInventory.length - i - 1;
            if (mc.thePlayer.inventory.armorInventory[armorIdx] == null) continue;
            Item armorItem = mc.thePlayer.inventory.armorInventory[armorIdx].getItem();
            int damage = mc.thePlayer.inventory.armorInventory[armorIdx].getItemDamage();
            armor.add(armorItem);
            float damageFraction = (float) (damage / (float) armorItem.getMaxDamage());
            armorDamage.add(damageFraction);
        }

        for (int i = 0; i < armor.size(); i++) {
            Item item = armor.get(i);
            float damage = armorDamage.get(i);
            int healthPct = (int) Math.round(100.0 - damage * 100.0);
            int space = 15;
            int itemX = this.x;
            int itemY = this.y + i * space;
            betterHUD.drawItemSprite(itemX, itemY, item, this);
            if (healthPct == 100) {
                itemX -= 2;
            } else if (healthPct < 10) {
                itemX += 3;
            }
            betterHUD.drawShadowedFont(healthPct + "%", itemX, itemY + 10, 0xFFFFFF);
        }

        if ((int) toggle == 0 && armor.size() > 0) {
            betterHUD.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    public void updateInGUI() {
        if (armor.size() < 1) {
            GlStateManager.enableBlend();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(770, 771);
            width = betterHUD.getFontRenderer().getStringWidth("No Armor");
            height = 10;
            betterHUD.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    coordWindow.getR(), coordWindow.getG(), coordWindow.getB(), coordWindow.getA(),
                    coordWindow.getBorderR(), coordWindow.getBorderG(), coordWindow.getBorderB(), coordWindow.getBorderA(),
                    coordWindow.getThickness());
            betterHUD.drawShadowedFont("No Armor", x, y, 0xFFFFFF);
            if ((int) toggle == 0) {
                betterHUD.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
            }
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
        }
    }

    @Override
    public void render() {
    }

    @Override
    public String getName() {
        return "Armor";
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
        return armor.size() * 14 + height;
    }

    @Override
    public void save() {
        data.clear();
        data.add((double) x);
        data.add((double) y);
        data.add((double) toggle);
        fileManager.setArray(data);
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
            toggle = data.get(2).intValue();
        }
    }
}
