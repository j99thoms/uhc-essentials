package com.j99thoms.uhcessentials.forge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VersionChecker {

    private static final Logger LOGGER = LogManager.getLogger(VersionChecker.class);
    private static final String CURRENT_VERSION = "1.2.1";
    private static final String VERSION_URL = "https://raw.githubusercontent.com/j99thoms/uhc-essentials/main/forge-1.8.9/version.txt";

    public void check(final Minecraft mc) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(new URL(VERSION_URL).openStream()));
                    String latestVersion = in.readLine().trim();
                    in.close();

                    if (!latestVersion.equals(CURRENT_VERSION)) {
                        mc.thePlayer.addChatMessage(new ChatComponentText(
                                "[UHC Essentials] A new version is available: " + latestVersion));
                    }
                } catch (Exception e) {
                    LOGGER.warn("Could not fetch UHC Essentials version info.");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
