# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK
./gradlew assembleRelease

# Install debug build to connected device/emulator
./gradlew installDebug

# Run unit tests
./gradlew test

# Run a single unit test class
./gradlew test --tests "dev.supergooey.mongoose.ExampleUnitTest"

# Clean build
./gradlew clean
```

## Architecture

This is a single-module Jetpack Compose Android application using Material 3.

- **Package**: `dev.supergooey.mongoose`
- **Min SDK**: 34 (Android 14)
- **Target/Compile SDK**: 36 (Android 15)
- **UI**: Jetpack Compose with Material 3, supports dynamic colors (Material You) on Android 12+
- **Build**: Gradle Kotlin DSL with version catalog (`gradle/libs.versions.toml`)

### Source Layout

- `app/src/main/java/` - Kotlin source code
- `app/src/main/java/dev/supergooey/mongoose/ui/theme/` - Compose theme (colors, typography, theme composables)
- `app/src/test/` - Unit tests (JUnit 4)

### Key Patterns

- Single Activity architecture with Compose (`MainActivity.kt`)
- Edge-to-edge layout enabled
- Centralized dependency versions in `gradle/libs.versions.toml`
