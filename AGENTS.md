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
