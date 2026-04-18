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
    api/
        GameContext.java      - Interface for game-state reads (pure Java, no Minecraft imports)
        GuiContext.java       - Interface for GUI/input context (pure Java, no Minecraft imports)
        HUDGraphics.java      - Interface for HUD drawing operations (pure Java, no Minecraft imports)
    forge/
        UHCEssentialsMod.java      - @Mod entry point, registers event handler
        HUDEventHandler.java       - Forge event subscriptions (tick + render overlay)
        ForgeGameContext.java      - Forge implementation of GameContext
        ForgeGuiContext.java       - Forge implementation of GuiContext
        ForgeHUDGraphics.java      - Forge implementation of HUDGraphics
        ForgeHUDConfigScreen.java  - Forge wrapper for HUDConfigScreen
        ForgeOptionMenu.java       - Forge wrapper for OptionMenu
        ForgeColorizer.java        - Forge wrapper for Colorizer
    gui/
        ScreenRequest.java    - Enum signalling screen lifecycle intent from logic classes to Forge wrappers
        HUDConfigScreen.java  - Config screen logic (pure Java, no Minecraft imports)
        OptionMenu.java       - Keybind settings logic (pure Java, no Minecraft imports)
        Colorizer.java        - Per-window color/theme editor logic (pure Java, no Minecraft imports)
    util/
        FileManager.java      - Config file I/O (.minecraft/UHC Essentials/configs/)
        VersionChecker.java   - Background version check (pure Java, no Minecraft imports)
    windows/
        BaseWindow.java       - Abstract base class for all HUD windows
        ThemedWindow.java     - Abstract base for themed windows; delegates color/theme methods to WindowTheme
        WindowManager.java    - Registry of all active windows, delegates update/render
        ...                   - Individual window classes
src/main/resources/
    mcmod.info                - Mod metadata shown in the Forge mod list
version.txt                   - Latest version string, fetched by VersionChecker on startup
```
