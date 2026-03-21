# Reference Material

Original 1.7.10 mod files, preserved for porting reference. **Do not modify.**

- `source-1.7.10/`: Java source code from the [original GitHub repo](https://github.com/abandonedaccount6235/UHC-Essentials).
- `compiled-1.7.10/`: Compiled `.class` files from the [distributed mod jar](https://www.reddit.com/r/ultrahardcore/comments/3mel2w/uhc_essentials_new_download_link/).
- `decompiled-1.7.10/`: CFR-decompiled output of `compiled-1.7.10/`. Generated with [CFR 0.152](https://github.com/leibnitz27/cfr/releases/tag/0.152).

## Source vs Compiled

The source (v1.1.2) and compiled jar (v1.2) are not identical. Key differences found by diffing:

- **`getToolTip()` added to every window** - returns a short description string shown as a hover
  tooltip in the config screen. Not present in source at all.
- **Two windows exist only in compiled** - `ArmorWindow` (armor durability HUD) and `TipWindow`
  (rotating tips). Use `decompiled-1.7.10/` as the source of truth for these.
- **`KillCounterWindow` was unfinished** - present in both, but its tooltip reads
  "Not implimented, how did you get it?!?" confirming it was experimental in v1.2.
- **Everything else** - structural differences between source and decompiled are CFR artifacts
  (obfuscated type names, formatting), not real logic changes.
