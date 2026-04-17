package com.j99thoms.uhcessentials.forge;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

import com.j99thoms.uhcessentials.api.GameContext;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class ForgeGameContext implements GameContext {

    private final Minecraft mc;

    public ForgeGameContext(Minecraft mc) {
        this.mc = mc;
    }

    @Override
    public double getPlayerX() {
        return mc.thePlayer.posX;
    }

    @Override
    public double getPlayerY() {
        return mc.thePlayer.posY;
    }

    @Override
    public double getPlayerZ() {
        return mc.thePlayer.posZ;
    }

    @Override
    public float getPlayerFacingYaw() {
        return MathHelper.wrapAngleTo180_float(mc.thePlayer.rotationYaw);
    }

    @Override
    public String getBiomeName() {
        BlockPos blockPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        return mc.theWorld.getBiomeGenForCoords(blockPos).biomeName;
    }

    @Override
    public List<String> getArmorItemNames() {
        ItemStack[] armorInventory = mc.thePlayer.inventory.armorInventory;
        ArrayList<String> armorItemNames = new ArrayList<String>();

        for (int i = 0; i < armorInventory.length; i++) {
            int armorIdx = armorInventory.length - i - 1;
            if (armorInventory[armorIdx] == null) continue;
            Item armorItem = armorInventory[armorIdx].getItem();
            armorItemNames.add(Item.itemRegistry.getNameForObject(armorItem).toString());
        }

        return armorItemNames;
    }

    @Override
    public List<Float> getArmorDurabilityFractions() {
        ItemStack[] armorInventory = mc.thePlayer.inventory.armorInventory;
        ArrayList<Float> armorDurabilityFractions = new ArrayList<Float>();
        
        for (int i = 0; i < armorInventory.length; i++) {
            int armorIdx = armorInventory.length - i - 1;
            if (armorInventory[armorIdx] == null) continue;
            int armorDamage = armorInventory[armorIdx].getItemDamage();
            int armorMaxDamage = armorInventory[armorIdx].getItem().getMaxDamage();
            armorDurabilityFractions.add((float) (armorDamage / (float) armorMaxDamage));
        }

        return armorDurabilityFractions;
    }

    @Override
    public int getArrowCount() {
        int arrowCount = 0;
        ItemStack[] inventory = mc.thePlayer.inventory.mainInventory;

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getItem() == Items.arrow) {
                arrowCount += inventory[i].stackSize;
            }
        }

        return arrowCount;
    }

    @Override
    public int getFPS() {
        return Minecraft.getDebugFPS();
    }

    @Override
    public float getGamma() {
        return mc.gameSettings.gammaSetting;
    }

    @Override
    public void setGamma(float gamma) {
        mc.gameSettings.gammaSetting = gamma;
    }
}
