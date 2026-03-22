# Integration Tests

Manual test checklist for verifying the 1.8.9 Forge port against the original v1.2 feature set.

Run with `./gradlew runClient` from `forge-1.8.9/`. Load into a singleplayer world for all tests.


## 1. HUD Windows on Load

All windows should appear immediately when entering a world, without any input required.

- [ ] CoordinateWindow: shows X, Y, Z values matching your position (F3 to cross-check)
- [ ] BiomeWindow: shows the current biome name
- [ ] FPSWindow: shows a non-zero FPS value
- [ ] ArrowCounterWindow: shows current arrow count (add arrows to inventory first)
- [ ] CompassWindow: shows compass item sprite
- [ ] ClockWindow: shows clock item sprite
- [ ] ArmorWindow: shows armor slots (equip a full set of armor to see all four)


## 2. CoordinateWindow

- [ ] X, Y, Z update as you walk around
- [ ] X, Y, Z match what is shown in F3 debug
- [ ] Direction label updates as you rotate: N, NE, E, SE, S, SW, W, NW
- [ ] Window width expands when coordinates get larger (go to a high X/Z value)


## 3. BiomeWindow

- [ ] Biome name changes as you cross biome boundaries
- [ ] Colors match CoordinateWindow (same background and border)


## 4. ArrowCounterWindow

- [ ] Shows correct arrow count matching inventory
- [ ] Flashes red when count drops below 5
- [ ] Clicking the window cycles through display modes (hidden / count / stacks)
- [ ] Count updates immediately when picking up or dropping arrows


## 5. ArmorWindow

- [ ] Shows all four armor slots when fully equipped (helmet, chestplate, leggings, boots)
- [ ] Durability display reflects actual item damage
- [ ] Empty slots are handled without crashing when no armor is worn


## 6. Config GUI (Right Shift by default)

- [ ] Right Shift opens the config screen
- [ ] All windows are visible and highlighted in the config screen
- [ ] Hovering over a window shows its tooltip text
- [ ] Dragging a window repositions it on screen
- [ ] Closing the config screen (Escape) keeps windows in their new positions
- [ ] Re-opening the config screen shows windows in the positions you left them


## 7. TipWindow

- [ ] TipWindow is hidden by default (does not show on the HUD)
- [ ] TipWindow appears in the config screen and shows a tip message
- [ ] Clicking the tip window cycles to a different tip


## 8. Config Persistence

- [ ] Reposition a window, close the GUI, quit to main menu, re-enter the world
- [ ] Window is still in the repositioned location (not reset to default)
- [ ] Check that config files exist at `.minecraft/UHC Essentials/configs/`
  (when using `./gradlew runClient`, this folder is at `forge-1.8.9/run/UHC Essentials/configs/`)


## 9. Colorizer

Note: right-clicking currently only opens the Colorizer for CoordinateWindow. 
The other windows share its color theme and cannot be individually colored yet (planned for a future refactor).

- [ ] Right-clicking CoordinateWindow in the config screen opens the Colorizer
- [ ] Adjusting the RGBA sliders changes the window background color in real time
- [ ] All windows sharing the theme update together
- [ ] Closing the Colorizer and re-opening the config screen shows the new colors
- [ ] Colors persist after quitting and re-entering the world


## 10. Fullbright Toggle

- [ ] Enter a dark cave or go underground at night
- [ ] Press the fullbright keybind (default: B, configurable in OptionMenu)
- [ ] Scene becomes fully lit (gamma maxed)
- [ ] Press again to restore normal gamma


## 11. Per-Window Show/Hide

- [ ] In the config screen, click a window to toggle it off (X marker should appear)
- [ ] Close the config screen - the window should be hidden
- [ ] Re-open the config screen, toggle it back on - window reappears
- [ ] Toggle state persists after quitting and re-entering the world


## 12. OptionMenu

- [ ] The config screen has an Options button that opens OptionMenu
- [ ] OptionMenu shows two keybind slots: drag key and fullbright key
- [ ] Rebinding works: press a new key, confirm it takes effect in-game
- [ ] Rebinding persists after quitting and re-entering the world


## 13. Version Checker

To test the update notification path, temporarily change `CURRENT_VERSION` to `"1.0"` in `VersionChecker.java` (do not commit).

- [ ] No chat message appears on load when `version.txt` matches `CURRENT_VERSION`
- [ ] Chat message `[UHC Essentials] A new version is available: 1.2.0` appears when versions differ
- [ ] No crash and no chat message when offline - only a warning in the FML log


## 14. OptiFine Compatibility

OptiFine cannot be tested via `./gradlew runClient`. Requires a real Minecraft installation.

1. Build the jar: `./gradlew build` (output in `forge-1.8.9/build/libs/`)
2. Install Minecraft 1.8.9 + Forge 11.15.1.2318 via the Minecraft Launcher
3. Drop the UHC Essentials mod jar into `.minecraft/mods/`
4. Download OptiFine HD U M5 for 1.8.9 via https://optifine.net/downloads 
5. Run the OptiFine installer on top of the Forge profile
6. Download a shader pack and place it in `.minecraft/shaderpacks/`

- [ ] Game launches without errors with OptiFine installed
- [ ] All HUD windows render correctly with shaders enabled
- [ ] No color glitches or windows rendering behind the world
- [ ] HUD still visible after toggling shaders on/off mid-session
