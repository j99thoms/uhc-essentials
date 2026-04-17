package com.j99thoms.uhcessentials.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VersionChecker {

    private static final Logger LOGGER = LogManager.getLogger(VersionChecker.class);
    private static final String CURRENT_VERSION = "1.2.1";

    private final String versionUrl;

    public VersionChecker(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public void check(final Consumer<String> notifier) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(new URL(versionUrl).openStream()));
                    String latestVersion = in.readLine().trim();
                    in.close();

                    if (!latestVersion.equals(CURRENT_VERSION)) {
                        notifier.accept("[UHC Essentials] A new version is available: " + latestVersion);
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
