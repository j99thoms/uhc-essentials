package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;

import com.j99thoms.uhcessentials.BetterHUD;

public class ArmorWindow extends BaseWindow {

    BetterHUD BH;

    private int x = 2;
    private int y = 102;

    private int r = 69;
    private int g = 69;
    private int b = 69;
    private int a = 150;

    private int r1 = 0;
    private int g1 = 0;
    private int b1 = 0;
    private int a1 = 255;

    private int width = 0;
    private int height = 0;

    private ArrayList<Item> armor = new ArrayList<Item>();
    private ArrayList<Float> armorDamage = new ArrayList<Float>();

    public static double toggle = 1;

    private float thickness = .8f;

    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager FM;

    private Minecraft mc;
    private CoordinateWindow CW;

    public ArmorWindow(BetterHUD BH, Minecraft mc, CoordinateWindow CW) {
        this.mc = mc;
        this.BH = BH;
        this.CW = CW;
        FM = new FileManager("Armor", 3);
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
            int idx = mc.thePlayer.inventory.armorInventory.length - i - 1;
            if (mc.thePlayer.inventory.armorInventory[idx] == null) continue;
            Item currItem = mc.thePlayer.inventory.armorInventory[idx].getItem();
            int damage = mc.thePlayer.inventory.armorInventory[idx].getItemDamage();
            armor.add(currItem);
            float pDamage = (float) (damage / (float) currItem.getMaxDamage());
            armorDamage.add(pDamage);
        }

        for (int i = 0; i < armor.size(); i++) {
            Item item = armor.get(i);
            float damage = armorDamage.get(i);
            int healthPct = (int) Math.round(100.0 - damage * 100.0);
            int space = 15;
            int cx = this.x;
            int cy = this.y + i * space;
            BH.drawItemSprite(cx, cy, item, this);
            if (healthPct == 100) {
                cx -= 2;
            } else if (healthPct < 10) {
                cx += 3;
            }
            BH.drawShadowedFont(healthPct + "%", cx, cy + 10, 0xFFFFFF);
        }

        if ((int) toggle == 0 && armor.size() > 0) {
            BH.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
        }

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    public void updateInGUI() {
        if (armor.size() < 1) {
            GlStateManager.enableBlend();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(770, 771);
            width = BH.getFontRenderer().getStringWidth("No Armor");
            height = 10;
            BH.drawHUDRectWithBorder(x - 1, y - 1, width + 2, getHeight() + 2,
                    CW.getR(), CW.getG(), CW.getB(), CW.getA(),
                    CW.getBorderR(), CW.getBorderG(), CW.getBorderB(), CW.getBorderA(),
                    CW.getThickness());
            BH.drawShadowedFont("No Armor", x, y, 0xFFFFFF);
            if ((int) toggle == 0) {
                BH.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
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
    public void setRGBA(int r, int g, int b, int a) {
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
    public void setBorderRGB(int r, int g, int b, int a) {
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
    public void setThickness(float t) {
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
        FM.setArray(data);
    }

    @Override
    public void load() {
        data.clear();
        data = FM.getArray();
        if (data.size() < 3) {
            save();
        } else {
            x = data.get(0).intValue();
            y = data.get(1).intValue();
            toggle = data.get(2).intValue();
        }
    }
}
