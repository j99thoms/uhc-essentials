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

# Build a distributable jar (output: build/libs/uhc-essentials-1.2.jar)
./gradlew build
```

## Structure

```
src/main/java/com/j99thoms/uhcessentials/
    UHCEssentialsMod.java     - @Mod entry point, registers event handler
    HUDEventHandler.java      - Forge event subscriptions (tick + render overlay)
    windows/                  - HUD window classes (ported from reference/source-1.7.10/)
src/main/resources/
    mcmod.info                - Mod metadata shown in the Forge mod list
```
