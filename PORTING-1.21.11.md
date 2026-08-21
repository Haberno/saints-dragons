# Minecraft 1.21.11 Porting Notes

This document records the Saint's Dragons port from the previous Minecraft API to
Minecraft 1.21.11. It also lists compatibility code that is intentionally preserved
but inactive until an equivalent 1.21.11 hook is implemented.

## Current status

- Common compiles as part of the Fabric build.
- Fabric builds and produces a remapped production jar.
- A Fabric client reaches the completed resource reload: Minecraft loaded 1,520
  recipes and 1,664 advancements, followed by the mod's crucible data, nine dragon
  attribute configurations, and seven dragon variant definitions.
- Forge compilation and jar remapping passed during the port, but Forge runtime
  startup is deferred. Fabric is the currently verified target.

## Build toolchain

- Gradle wrapper: 9.4.0. This supplies the Gradle API required by the current Loom
  and Architectury plugin combination and fixes the original
  `Configuration.extendsFrom(Provider[])` project-evaluation failure.
- Architectury Plugin: 3.5.169.
- Architectury Loom: 1.17.491.
- Java toolchain: 21.
- Minecraft: 1.21.11.
- Fabric Loader: 0.19.3.
- Fabric API: 0.141.6+1.21.11.
- GeckoLib: 5.4.2.

## Ported systems

- Migrated GeckoLib 4 animatables, controllers, renderers, layers, models, and
  render-state data to GeckoLib 5 APIs.
- Migrated entity, AI, damage, movement, interaction, item, armor, particle,
  recipe, menu, loot, camera, rendering, and registry APIs used by 1.21.11.
- Migrated Fabric networking to typed payload registration and handling.
- Migrated Fabric resource reload listeners, key mappings, entity-part hooks,
  loot modification, spawn eggs, client registration, camera/FOV handling, and
  client-level entity access.
- Added the mandatory registry IDs to block and item properties before registration.
- Replaced the removed `PathfinderMob.tickLeash` mixin path with native
  `DragonEntity` leash-distance and elastic-interaction overrides.
- Updated the dismount input packet hook to the 1.21.11 `Input` record packet.
- Updated damage mixins from the removed `LivingEntity.hurt` target to
  `hurtServer`.
- Updated camera mixin descriptors from `BlockGetter` to the 1.21.11 `Level`
  parameter.

## Resource migration

- Moved GeckoLib geometry to `assets/saintsdragons/geckolib/models` and animations
  to `assets/saintsdragons/geckolib/animations`, with matching Java resource IDs.
- Renamed data-pack folders to the singular 1.21.11 names: `advancement`,
  `loot_table`, `recipe`, and `structure`.
- Converted all 50 recipes to the current ingredient and result formats.
- Converted advancement display icons from `item` to `id`.
- Added 105 item-definition files under `assets/saintsdragons/items` for the new
  item model loading system.

## Preserved but inactive code

No legacy feature source was intentionally deleted. Code whose old target no longer
exists is kept in-tree and excluded from compilation or its mixin configuration.

- Fabric: legacy Blood Tempest afterimages, rider/living-entity render hooks,
  entity-dispatch hooks, the old GameRenderer hook, the old recipe-book hook,
  the old Draconian armor renderer, the old ClientLevel accessor, and the static
  Codex portrait renderer.
- Forge: Jade, Epic Fight render compatibility, ability/path debug renderers,
  the old UI handler, Blood Tempest afterimages, rider/living-entity render hooks,
  and the old GameRenderer hook.
- The Fabric `reach-entity-attributes` 2.4.0 runtime dependency is commented out
  because it injects into a removed container method and crashes 1.21.11 startup.
  The related mod source remains available for a native attribute port.
- The old Fabric and Forge Pathfinder leash mixin sources remain in-tree but are
  inactive because the behavior now lives directly in `DragonEntity`.

## Known parity gaps

- The preserved render hooks above still need render-state-native replacements.
- Custom armor currently uses the compatible fallback path rather than the old
  loader-specific renderer.
- Codex static portraits are disabled pending a GeckoLib 5 render-state port.
- The Mossback adult item/model presentation still needs visual review.
- Raevyx lightning uses the current compatible approximation and needs gameplay
  comparison with the old effect.
- UI tint/blend behavior and Dragonheart reach behavior need in-game parity review.
- No existing world was opened during this pass, so saves, structures, combat,
  riding, breeding, and boss encounters still need gameplay testing.

## Forge note

Forge Jade is fully commented out and its source is preserved through a source-set
exclude. Forge 61's development launcher first exposed a duplicate common-module
classpath entry; the duplicate path is now commented out. Startup then reached an
internal `ByteBufCodecs$11` constructor mismatch in Loom's generated Forge Minecraft
jar. Per the current scope, that Forge-only runtime issue is documented and deferred.
It does not affect the verified Fabric jar.

## Verification

Passed on August 21, 2026:

```text
gradlew :fabric:build --no-daemon --console=plain --max-workers=1
BUILD SUCCESSFUL
```

The Fabric client was also launched through `:fabric:runClient` and completed mod
initialization and resource loading without a Saint's Dragons crash. The development
client was then closed manually; offline authentication warnings from the dev account
and JEI's optional Amecs warning were non-fatal.

