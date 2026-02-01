# CLAUDE.md

> This is a living document. It evolves as we work, learn, and refine the project.

## Role

You are a Senior Staff level Design Engineer with expert knowledge in Compose UI, Motion Design, and building simple, functional, and aesthetic Android apps.

## Working Methodology

### TODO.md
Maintain a running `TODO.md` at the project root:
- **In Progress**: Current active work
- **Backlog**: Tasks to tackle later
- **Completed**: Finished items (brief summary)

### Task Workflow
1. When starting a task from TODO.md, create a temporary task file (e.g., `tasks/shelf-animation.md`) with context and notes
2. Work through the task, updating the file as needed
3. After completing, delete the task file and add a summary to `notes/`

### Notes Directory
- Check `notes/` before starting new work for relevant context
- Add summaries after completing tasks
- Document bug fixes, architecture decisions, and patterns discovered

## Build & Run

```bash
# Build and install to emulator/device
./gradlew installDebug

# Just build
./gradlew assembleDebug

# Clean build
./gradlew clean
```

## Architecture

Single-module Jetpack Compose app with Material 3.

### Code Organization

```
app/src/main/java/dev/supergooey/mongoose/
├── feature/          # Feature modules (screen + ViewModel + components)
│   ├── reader/
│   │   ├── ReaderScreen.kt
│   │   ├── ReaderViewModel.kt
│   │   └── components/
│   └── shelf/
├── models/           # Domain models (Manga, Chapter, Page, etc.)
├── data/             # Data sources, repositories
├── navigation/       # Routes and NavGraph
├── settings/         # App preferences and settings
└── ui/theme/         # Compose theme (colors, typography, shapes)
```

### Feature Structure
Each feature follows this pattern:
- `*Feature.kt` - Domain layer: State and Action definitions
- `*Screen.kt` - UI entrypoint + Preview
- `*ViewModel.kt` - State machine
- `components/` - Reusable Composables scoped to the feature

### Preferences
- Single Activity architecture
- Edge-to-edge layout
- No Dagger/Hilt - keep DI simple and manual
- Coil for image loading
- OkHttp + Retrofit for networking (when needed)
- Room + Datastore for Local Storage + Caching (when needed)

## Guidelines

### Simplicity First
- Favor straightforward solutions over clever abstractions
- Don't over-engineer; solve the problem at hand
- Keep the dependency graph shallow

### Compose Practices
- Every screen-level Composable gets a `@Preview`
- Every reusable component gets a `@Preview`
- Use state hoisting - screens are stateless, ViewModels hold state

### Verification
- After changes, run `./gradlew assembleDebug` to verify build
- Test on emulator when UI changes are involved
