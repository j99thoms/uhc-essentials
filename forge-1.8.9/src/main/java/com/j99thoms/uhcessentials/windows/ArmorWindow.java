package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import com.j99thoms.uhcessentials.GameContext;
import com.j99thoms.uhcessentials.HUDGraphics;

public class ArmorWindow extends BaseWindow {

    private static final int DEFAULT_X = 2;
    private static final int DEFAULT_Y = 102;

    private int width = 14;

    private List<String> armorNames = new ArrayList<String>();
    private List<Float> armorDurabilityFractions = new ArrayList<Float>();

    private ArrayList<Double> data = new ArrayList<Double>();
    private final FileManager fileManager;

    private final GameContext gameContext;
    private final WindowTheme theme;

    public ArmorWindow(HUDGraphics hudGraphics, GameContext gameContext, WindowTheme theme) {
        super(hudGraphics);
        this.gameContext = gameContext;
        this.theme = theme;
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        fileManager = new FileManager("Armor", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how damaged your armor is";
    }

    @Override
    public void setToDefault() {
        setX(DEFAULT_X);
        setY(DEFAULT_Y);
        save();
    }

    @Override
    public void update() {
        armorNames = gameContext.getArmorItemNames();
        armorDurabilityFractions = gameContext.getArmorDurabilityFractions();
    }

    @Override
    public void render() {
        for (int i = 0; i < armorNames.size(); i++) {
            Item item = (Item) Item.itemRegistry.getObject(new ResourceLocation(armorNames.get(i)));
            float damage = armorDurabilityFractions.get(i);
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

        if ((int) toggle == 0 && armorNames.size() > 0) {
            hudGraphics.drawShadowedFont("X", x - 2, y - 2, 0xFFFFFF);
        }
    }

    public void updateInGUI() {
        if (armorNames.size() < 1) {
            GlStateManager.enableBlend();
            GlStateManager.depthMask(false);
            GlStateManager.blendFunc(770, 771);
            int noArmorWidth = hudGraphics.getStringWidth("No Armor");
            hudGraphics.drawHUDRectWithBorder(x - 1, y - 1, noArmorWidth + 2, 12,
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
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return armorNames.size() * 14;
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
