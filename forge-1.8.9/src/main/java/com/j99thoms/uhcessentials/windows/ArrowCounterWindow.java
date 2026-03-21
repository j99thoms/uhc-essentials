package com.j99thoms.uhcessentials.windows;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import com.j99thoms.uhcessentials.BetterHUD;

public class ArrowCounterWindow extends BaseWindow {

    BetterHUD BH;

    private int x = 16;
    private int y = 68;

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
    private int flash = 0;

    private float thickness = .8f;
    private ArrayList<Double> data = new ArrayList<Double>();
    private FileManager FM;
    private ItemStack[] inventroy = new ItemStack[36];
    private Item item;
    private int nextFlash = 2;
    private boolean shouldFlash = true;
    private int count = 0;
    private int timer = 0;
    private int secondTimer = 0;
    private long lastTime;

    private Minecraft mc;

    private double toggle = 1;

    public ArrowCounterWindow(BetterHUD BH, Minecraft mc) {
        this.mc = mc;
        this.BH = BH;
        FM = new FileManager("Arrow", 3);
        load();
    }

    @Override
    public String getToolTip() {
        return "Shows how many arrows you have. Click to change modes";
    }

    @Override
    public void setToDefault() {
        setX(16);
        setY(68);
        save();
    }

    @Override
    public void toggle() {
        toggle = toggle == 0 ? 1 : (toggle == 1 ? 2 : 0);
    }

    @Override
    public int getToggled() {
        if (toggle == 2) {
            return 1;
        }
        return (int) toggle;
    }

    @Override
    public void update() {
        count = 0;
        inventroy = mc.thePlayer.inventory.mainInventory;
        for (int i = 0; i < inventroy.length; i++) {
            if (inventroy[i] != null) {
                if (inventroy[i].getItem() == Items.arrow)
                    count += inventroy[i].stackSize;
            }
        }

        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);

        if (count <= 5 && shouldFlash) {
            if (timer < 3 && System.currentTimeMillis() - lastTime >= 500L) {
                if (System.currentTimeMillis() - lastTime >= 1000L) {
                    lastTime = System.currentTimeMillis();
                    timer++;
                } else {
                    BH.drawHUDRectWithBorder(getX() + 2, getY() + 2, getWidth() + 1, getHeight() + 1,
                            255, 0, 0, 255, 0, 0, 0, 255, 1.0);
                }
            } else if (timer >= 3) {
                shouldFlash = false;
            }
        } else if (count > 5) {
            timer = 0;
            shouldFlash = true;
            nextFlash = 4;
            lastTime = System.currentTimeMillis();
        }

        BH.drawItemSprite(x, y, Items.arrow, this);

        if (toggle != 2 || count < 64)
            mc.fontRendererObj.drawStringWithShadow(count + "", x + 11, y + 9, 0xffffffff);
        else if (count > 64 && count % 64 != 0)
            mc.fontRendererObj.drawStringWithShadow("[" + (int) Math.floor(count / 64) + "]+" + count % 64, x + 11, y + 9, 0xffffffff);
        else if (count >= 64 && count % 64 == 0)
            mc.fontRendererObj.drawStringWithShadow("[" + (int) Math.floor(count / 64) + "]", x + 11, y + 9, 0xffffffff);

        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();

        if ((int) toggle == 0)
            mc.fontRendererObj.drawStringWithShadow("X", x, y, 0xffffffff);
        if ((int) toggle == 1 && CoordsGUI.guiOpen)
            mc.fontRendererObj.drawStringWithShadow("Sum", x, y, 0xffffffff);
        if ((int) toggle == 2 && CoordsGUI.guiOpen)
            mc.fontRendererObj.drawStringWithShadow("Stacks", x, y, 0xffffffff);
    }

    @Override
    public String getName() {
        return "ArrowCounter";
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
        return 13;
    }

    @Override
    public int getHeight() {
        return 13;
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
