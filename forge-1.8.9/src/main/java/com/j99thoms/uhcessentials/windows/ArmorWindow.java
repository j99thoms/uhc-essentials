package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;

import com.j99thoms.uhcessentials.HUDGraphics;

public class ArmorWindow extends BaseWindow {

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 102;

    private int x = DEFAULT_X;
    private int y = DEFAULT_Y;

    private int width = 0;
    private int height = 0;

    private ArrayList<Item> armor = new ArrayList<Item>();
    private ArrayList<Float> armorDamage = new ArrayList<Float>();

    private double toggle = 1;

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final Minecraft mc;
    private final WindowTheme theme;

    public ArmorWindow(HUDGraphics hudGraphics, Minecraft mc, WindowTheme theme) {
        super(hudGraphics);
        this.mc = mc;
        this.theme = theme;
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
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
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
            hudGraphics.drawItemSprite(itemX, itemY, item);
            if (healthPct == 100) {
                itemX -= 2;
            } else if (healthPct < 10) {
                itemX += 3;
            }
            hudGraphics.drawShadowedFont(healthPct + "%", itemX, itemY + 10, 0xFFFFFF);
        }

        if ((int) toggle == 0 && armor.size() > 0) {
            hudGraphics.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    public void updateInGUI() {
        if (armor.size() < 1) {
            GlStateManager.enableBlend();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(770, 771);
            width = hudGraphics.getStringWidth("No Armor");
            height = 10;
            hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    theme.getR(), theme.getG(), theme.getB(), theme.getA(),
                    theme.getBorderR(), theme.getBorderG(), theme.getBorderB(), theme.getBorderA(),
                    theme.getThickness());
            hudGraphics.drawShadowedFont("No Armor", x, y, 0xFFFFFF);
            if (getToggled() == 0) {
                hudGraphics.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
            }
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
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
