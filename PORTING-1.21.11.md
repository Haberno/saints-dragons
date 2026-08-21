# Minecraft 1.21.11 Porting Notes

This file tracks the changes made while porting Saint's Dragons to Minecraft 1.21.11.
It is intentionally updated alongside the code so unfinished compatibility work stays visible.

## Build toolchain

- Updated the Gradle wrapper from 9.2.1 to 9.4.0. Loom calls
  `Configuration.extendsFrom(Provider[])`, which is only available starting in Gradle 9.4.
- Updated Architectury Plugin from 3.4.164 to 3.5.169.
- Kept Architectury Loom on the existing 1.17 snapshot line (currently resolving to 1.17.491).
- Confirmed that the original Gradle project-evaluation failure is fixed.

## Dependency compatibility

- GeckoLib 5.4.2 is required for the Minecraft 1.21.11 rendering pipeline. The old
  GeckoLib 4 APIs are being migrated rather than forcing an unsupported GeckoLib version.
- EMI remains disabled because the configured EMI artifacts do not support Minecraft 1.21.11.
- Forge Jade and Forge JEI integrations remain disabled until matching 1.21.11 artifacts are available.
- Common JEI API and Fabric JEI remain enabled with their configured 1.21.11-compatible artifacts.

## Source changes completed

- Migrated Minecraft class/package moves for entity spawn reasons, path types, relative movement,
  illagers, zombies, villagers, boats, fish, arrows, and render types.
- Migrated item `use` overrides from the removed `InteractionResultHolder<ItemStack>` API to
  `InteractionResult`.
- Migrated the first mechanical GeckoLib 4-to-5 package moves, including animatables,
  animation controllers, animation tests, cached model classes, renderers, and render layers.

## Work in progress

- Move custom GeckoLib model posing from the removed `GeoModel#setCustomAnimations` hook to
  GeckoLib 5 render-state bone snapshots.
- Port GeckoLib entity, armor, item, and specialty renderers to the deferred render-state API.
- Port armor items from the removed `ArmorItem` class to item properties and equipment components.
- Port custom particles to `SingleQuadParticle` and the codec/stream-codec particle data API.
- Port the Draconian Nucleus and Draconic Crucible models from the removed `HierarchicalModel` API.
- Relocate GeckoLib model and animation assets to the GeckoLib 5 resource directories.
- Compile and verify common, Fabric, Forge, then the complete distribution build.

## Verification log

- `gradlew help`: passes project evaluation and reaches dependency resolution; the original
  `extendsFrom(Provider[])` error no longer occurs.
- `gradlew :common:compileJava`: currently fails in the in-progress GeckoLib 5, armor,
  particle, and model migrations listed above.

