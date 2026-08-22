# Fast dev iteration

Notes on getting from "I changed a line" to "I see it in game" quickly.
Everything below is already wired into the build; the IntelliJ items are
one-time settings you have to click yourself.

## What changed in the build

**`gradle.properties`**

- `org.gradle.daemon=true` — the daemon was off, which meant every single
  Gradle command paid full JVM startup plus a cold re-configuration of the
  whole Architectury build. This is the single biggest win for `runClient`
  from the terminal or the Gradle tool window.
- `org.gradle.parallel=true`, `org.gradle.caching=true` — parallel project
  execution and the local build cache, so `:common` work is not redone.
- `org.gradle.jvmargs=-Xmx4G -XX:MaxMetaspaceSize=1G` — 3G was tight for a
  three-module Loom build and caused GC thrash during configuration.
- `dev_world=New World` — the client boots straight into that save instead of
  the main menu. Set it to any folder under `fabric/run/saves` (Fabric) or
  `run/saves` (Forge). Leave it empty to stop at the menu. A name that does
  not exist on disk is ignored, so this can never break a launch.

If parallel execution ever misbehaves with Loom, `org.gradle.parallel=false`
is a safe rollback; keep the daemon on regardless.

**`fabric/build.gradle` / `forge/build.gradle`** — the client and server runs
now pass:

```
-XX:+IgnoreUnrecognizedVMOptions
-XX:+AllowEnhancedClassRedefinition
-XX:+AllowRedefinitionToAddDeleteMethods
```

On the JetBrains Runtime these let HotSwap redefine classes that gained or
lost methods and fields, not just method bodies. On a stock JDK the flags are
dropped silently, so nothing breaks either way.

**`build.gradle`** — `run*` tasks pick a JetBrains Runtime toolchain when
Gradle can see one, and fall back to the plain Java 21 toolchain otherwise.

## One-time IntelliJ setup

1. **Build with the IDE, not Gradle.**
   Settings → Build, Execution, Deployment → Build Tools → Gradle →
   *Build and run using* and *Run tests using* → **IntelliJ IDEA**.
   This is what makes a relaunch skip Gradle configuration entirely, and it is
   what makes HotSwap able to recompile a single class.

2. **Point the run config at the JetBrains Runtime.** See the walkthrough in
   "Getting a JBR" below. Without a JBR, HotSwap only swaps method bodies.

3. **Turn on background compilation.**
   - Settings → Build → Compiler → **Build project automatically** ✓
   - Settings → Advanced Settings → **Allow auto-make to start even if the
     developed application is currently running** ✓
   - Settings → Build → Debugger → HotSwap → *Reload classes after
     compilation* → **Always**

## Getting a JBR

The JetBrains Runtime is a JDK fork with enhanced class redefinition built in.
IntelliJ already runs on one; the cleanest way to get a copy both the IDE and
Gradle can use is to let IntelliJ download one:

1. **Run → Edit Configurations…** → select *Minecraft Client (:fabric)*.
2. Find the **JRE** dropdown (in the *Build and run* row; if it is not shown,
   *Modify options* → *Alternative JRE*).
3. Open the dropdown → **Download JDK…** at the bottom.
4. Version **21**, Vendor **JetBrains Runtime** → **Download**.
   It lands in `C:\Users\<you>\.jdks\jbr-21.x.x`.
5. Make sure the JRE field now shows that `jbr-21…` entry → **Apply**.
6. Repeat step 2–5 for *Forge Client (:forge)* if you use it.

Gradle auto-detects `~/.jdks`, so `./gradlew runClient` picks the same runtime
up with no extra configuration. Check with:

```
./gradlew -q javaToolchains
```

Look for an entry whose vendor is **JetBrains**. If it is missing, add the path
to `gradle.properties` (forward slashes, no escaping needed):

```
org.gradle.java.installations.paths=C:/Users/<you>/.jdks/jbr-21.0.9
```

A Gradle launch prints `[dev] launching on JetBrains Runtime: …` when it took
effect. An IDE launch has no such line — verify it instead by adding a method
to a live class and pressing `Ctrl+F9`: with a JBR you get "1 class reloaded",
without one you get "class not reloadable: schema change".

To use the IDE's own bundled runtime instead of a downloaded copy, find its
path with **Help → Find Action → "Choose Boot Java Runtime for the IDE"** and
paste that `…\jbr` path into the JRE field. It works, but the path changes
whenever the IDE updates, which is why the `.jdks` copy is the better default.

## The loop

- **Java changes:** launch with **Debug** (the bug icon), not Run — HotSwap
  needs the debugger attached. Edit, save, and the class reloads. Bodies of
  methods that are currently on the stack take effect the next time they are
  called. If IntelliJ says a class cannot be reloaded, the change touched
  something structural (superclass, interfaces, enum constants, a mixin) and
  you do need a relaunch.
- **Mixins never hot-swap.** Anything under `*/mixin/**` requires a restart.
- **Assets** (textures, models, `.geo.json`, `.animation.json`, lang):
  build (`Ctrl+F9`) then **F3+T** in game.
- **Data** (recipes, loot tables, tags, advancements): build, then `/reload`.
  No restart for either.
- **Relaunch when you must:** with the daemon on and the IDE doing the
  compile, this is mostly Minecraft's own startup, and `dev_world` removes the
  main-menu and world-load round trip from it.

## Terminal launches

```
./gradlew :fabric:runClient --offline
```

`--offline` skips the remote check on the `1.17-SNAPSHOT` Loom plugin and the
mod dependencies; drop it after changing a dependency version.
