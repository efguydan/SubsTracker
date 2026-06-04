# AGENTS.md

Guidance for working on this Kotlin Multiplatform (KMP) project.

## Project layout
- `shared/`: KMP shared module (common + platform sources).
- `composeApp/`: Android app using Jetpack Compose UI.
- `iosApp/`: iOS app using SwiftUI UI.

## Architecture expectations
- **Current state**: screens currently live in `composeApp/androidMain/`. The KMP plan below (shared VMs, use cases, SQLDelight repos) is the target — don't scaffold those layers piecemeal in unrelated PRs.
- Prefer putting business logic, models, and domain rules in `shared/`.
- Keep UI state and UI-specific logic in platform apps.
- Use expect/actual only when truly platform-specific.

## Kotlin/Gradle
- Keep KMP source sets clean: `commonMain`, `androidMain`, `iosMain`, and matching test source sets.
- Add dependencies in the correct source set; avoid leaking Android-only deps into `commonMain`.
- Prefer `Gradle` Kotlin DSL edits in `build.gradle.kts` files.

## Android (Compose)
- UI goes in `composeApp/` using Compose.
- Prefer stateless Composables; use state hoisting where possible.

### Navigation & screen structure
- Navigation uses `androidx.navigation-compose` (`NavController` + `NavHost`) with **type-safe routes** — `@Serializable data object`s, enabled by the `kotlinx.serialization` plugin. No string routes.
- Naming: the navigation route is the object `XRoute` (e.g. `TimelineRoute`); the screen composable is `XScreen` (e.g. `TimelineScreen`). Register as `composable<XRoute> { XScreen() }`. (This supersedes the older `*Route`-container/`*Screen`-content split.)
- The app root (`HomeShell`) is a **thin shell** owning only the persistent bottom navigation bar; it adapts `HomeTab` ↔ routes. The bottom-nav routes live in `ui/navigation/HomeRoutes.kt`.
- **Each screen owns its own `Scaffold`** (its own top bar + optional FAB + content) via the `HomeScreenScaffold` helper in `ui/home/`. Per-screen FABs live on the screen, not the root.
- Don't put preview/sample state on a public composable's default args (see *Preview data*). Keep the screen's public composable parameterless (or hoist real state); render placeholder/sample data through a `private` content composable.

### Proton design system
- All UI consumes the Proton system in `composeApp/.../ui/proton/`. Read `ProtonTheme.colors.*` and `ProtonTheme.typography.*` from screens — not `MaterialTheme.colorScheme.*` / `MaterialTheme.typography.*`.
- Use `ProtonDimension` tokens (`Spacing*`, `Corner*`, `ComponentSize*`) for layout values. If a value isn't a token, add it rather than inline a raw `dp`.
- Prefer Proton components (`ProtonText`, `ProtonButton`, …) over raw Material counterparts.
- Light/dark parity: every field on `LightColorScheme` / `ProtonColorPalette.light()` must also be on the dark variants.

### Fonts
- Use the `GoogleFont` provider (`androidx.compose.ui.text.googlefonts`) with one `Font(...)` per weight. XML `font-family` resources with a bare provider query return weight 400 only — Compose then synthesises bold.

### Scaffold and insets
- `Scaffold`'s `bottomBar` slot is **not** inset automatically. Custom bottom bars must apply `Modifier.windowInsetsPadding(WindowInsets.navigationBars)`. Prefer M3 `NavigationBar` when the design allows it.

### Accessibility
- Tab bars use `Modifier.selectable(selected, role = Role.Tab, onClick = ...)`, not `clickable`.
- Keep clickable surfaces ≥ 48 dp (`heightIn(min = ProtonDimension.ComponentSize48)` or weight-fill the parent).
- When an icon and text label describe the same action, set `Icon(contentDescription = null)` to avoid TalkBack double-reads.

### Preview data
- `@Preview` fixtures are `internal` or live in a `PreviewParameterProvider` — never expose preview state on the public API.

## iOS (SwiftUI)
- UI goes in `iosApp/` using SwiftUI.
- Treat shared KMP APIs as the source of truth for data and logic.

## Tests
- Shared logic tests belong in `shared/src/commonTest`.
- Platform-specific tests should live in `androidTest` or `iosTest` where applicable.

## Safe defaults
- Keep changes minimal and scoped.
- Avoid regenerating project files unless explicitly requested.

## Architecture Plan (KMP)
- Clean Architecture with strict separation of concerns.
- Shared ViewModels (core logic) in `shared/` with optional platform mini ViewModel adapters.
- Reactive: `Flow` for streams + `suspend` for commands.
- Storage: SQLDelight in `shared/`.
- DI: Metro (compile-time DI, `dev.zacsweers.metro`) — see *Dependency injection* below.
- UI State: `StateFlow<UiState>` + `UiEvent`.
- Dispatchers injected (abstracted).
- Errors: `Result` + `UiEvent` mapping.
- Module layering:
  - `shared/`:
    - `domain/`: entities + use cases.
    - `data/`: repository interface + SQLDelight implementation.
    - `presentation/`: shared ViewModels with `uiState` and `events`.
  - `composeApp/`: Android Compose UI + navigation.
  - `iosApp/`: SwiftUI UI + wrapper for shared ViewModels as needed.
 - Data flow:
   - UI sends `UiAction` to shared VM.
   - VM calls use cases, updates `UiState`, emits `UiEvent`.
   - Repos expose `Flow` for streams + `suspend` for mutations.
 - Dispatchers:
   - `AppDispatchers` interface in `shared/` (implemented as `concurrency/AppDispatchers.kt` + per-platform `DefaultAppDispatchers`).
   - Android: `Dispatchers.IO/Default/Main`.
   - iOS: `Dispatchers.Default/Main`.
 - Dependency injection (Metro):
   - The Metro compiler plugin is applied to `shared/` only; `composeApp`/`iosApp` consume the graph via plain helper functions: `createAndroidAppGraph(context)` (called from `SubsTrackerApplication`) and `createIosAppGraph()` (exported in the `Shared` framework).
   - Topology: plain `interface AppGraph` in `commonMain` holds the shared accessors; the concrete `@DependencyGraph(AppScope::class)` interfaces (`AndroidAppGraph`/`IosAppGraph`) live in platform source sets — Metro requires the final graph to be platform-specific so it can see platform bindings.
   - Convention: data/domain classes stay free of DI annotations. Bindings live in `@ContributesTo(AppScope::class)` provider interfaces under `shared/.../di/` (e.g. `DataProviders`). App-wide singletons use `@SingleIn(AppScope::class)`.
   - Don't expose raw stdlib types (e.g. `CoroutineContext`) as graph types; map them from `AppDispatchers` inside providers.
 - Error handling:
   - Use cases return `Result`.
   - VM maps failures to `UiEvent.Error` and optional `UiState.error`.
 - SQLDelight repository surface:
   - `observeSubscriptions(): Flow<List<Subscription>>`
   - `observeTimeline(horizonDays: Int): Flow<List<PaymentEvent>>`
   - `add/update/delete` suspend functions.
 - Diagram:
```
UI (Compose / SwiftUI)
  |
  v
Platform UI Adapters (optional)
  |
  v
Shared ViewModels (StateFlow + UiEvent)
  |
  v
Use Cases (suspend + Flow)
  |
  v
Repository Interfaces
  |
  v
SQLDelight Data Sources
```
 - Module boundaries:
```
composeApp/ (Android UI)      iosApp/ (SwiftUI UI)
          \                          /
           \                        /
            v                      v
              shared/
                - presentation/ (Shared VMs)
                - domain/ (Entities + Use cases)
                - data/ (Repo + SQLDelight)
```
