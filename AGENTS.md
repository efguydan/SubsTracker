# AGENTS.md

Guidance for working on this Kotlin Multiplatform (KMP) project.

## Project layout
- `shared/`: KMP shared module (common + platform sources).
- `composeApp/`: Android app using Jetpack Compose UI.
- `iosApp/`: iOS app using SwiftUI UI.

## Architecture expectations
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
- DI: Koin.
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
   - `AppDispatchers` interface in `shared/`.
   - Android: `Dispatchers.IO/Default/Main`.
   - iOS: `Dispatchers.Default/Main`.
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
