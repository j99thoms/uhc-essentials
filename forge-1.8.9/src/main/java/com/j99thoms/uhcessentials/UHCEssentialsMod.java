package com.j99thoms.uhcessentials;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = UHCEssentialsMod.MODID, name = "UHC Essentials", version = "1.2.0")
public class UHCEssentialsMod {

    public static final String MODID = "uhcessentials";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new HUDEventHandler());
    }
}
