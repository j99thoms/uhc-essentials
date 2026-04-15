# forge-1.8.9

Minecraft 1.8.9 + Forge port of UHC Essentials.

## Requirements

- JDK 8
- `JAVA_HOME` set to your JDK 8 installation

## Build

**One-time setup:**
```bash
# First time only - downloads Minecraft, Forge, and deobfuscates sources
./gradlew setupDecompWorkspace
```

```bash
# Launch the game in a dev environment
./gradlew runClient

# Build a distributable jar (output: build/libs/uhc-essentials-1.2.1.jar)
./gradlew build
```

## Structure

```
src/main/java/com/j99thoms/uhcessentials/
    UHCEssentialsMod.java     - @Mod entry point, registers event handler
    HUDEventHandler.java      - Forge event subscriptions (tick + render overlay)
    VersionChecker.java       - Background version check against forge-1.8.9/version.txt
    HUDGraphics.java          - Interface for HUD drawing operations (pure Java, no Minecraft imports)
    GameContext.java          - Interface for game-state reads (pure Java, no Minecraft imports)
    ForgeHUDGraphics.java     - Forge implementation of HUDGraphics
    ForgeGameContext.java     - Forge implementation of GameContext
    windows/
        BaseWindow.java       - Abstract base class for all HUD windows
        Colorizable.java      - Interface for windows that support color/theme customization
        ThemedWindow.java     - Abstract base for themed windows; implements Colorizable via WindowTheme delegation
        FileManager.java      - Config file I/O (.minecraft/UHC Essentials/configs/)
        WindowManager.java    - Registry of all active windows, delegates update/render
        ...                   - Individual window classes
    gui/
        HUDConfigScreen.java  - Config overlay screen (drag windows, toggle HUD, open options)
        OptionMenu.java       - Keybind settings screen
        Colorizer.java        - Per-window color/theme editor
src/main/resources/
    mcmod.info                - Mod metadata shown in the Forge mod list
version.txt                   - Latest version string, fetched by VersionChecker on startup
```
