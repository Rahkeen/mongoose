# Shelf Container Transform Animation

## Summary
Implemented shared element transitions (container transform) between the manga shelf and reader screens, along with a premium card design.

## Key Implementation Details

### Shared Element Transitions
- Wrap `NavHost` with `SharedTransitionLayout` to enable shared elements across navigation
- Pass `sharedTransitionScope` and `animatedVisibilityScope` to both screens
- Use `sharedBounds` modifier with matching keys (`"manga-${manga.id}"`) on both the card and reader container
- Use `sharedElement` for the cover image specifically if needed

### Animating Non-Shared Elements
**Preferred approach:** Use `animateEnterExit` modifier within `AnimatedVisibilityScope`:
```kotlin
with(animatedVisibilityScope) {
    ShelfTopBar(
        modifier = Modifier.animateEnterExit(
            enter = fadeIn(),
            exit = fadeOut()
        )
    )
}
```
This is the idiomatic way to animate elements that aren't part of the shared element but should fade/slide during screen transitions.

## Issues Encountered & Solutions

### 1. Immersive Mode Transition Glitches
**Problem:** Switching between immersive (hidden system bars) and normal mode during shared element transitions caused visual glitches - scrim over status bar, jumping UI elements.

**Solution:** Removed immersive mode entirely for the reader. The black reader background naturally extends behind the status bar in edge-to-edge mode. Simpler and cleaner.

### 2. White Flash During Transitions
**Problem:** Brief white flash visible during navigation transitions.

**Solution:** Wrap `SharedTransitionLayout` with a `Box` that has the app's dark surface color as background:
```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surfaceContainerLowest)
) {
    SharedTransitionLayout { ... }
}
```

### 3. Status Bar Icon Colors
**Problem:** After leaving the reader (dark background, light icons), returning to shelf showed wrong icon colors.

**Solution:** Each screen manages its own status bar appearance:
- **Shelf:** Uses `isSystemInDarkTheme()` to set appropriate icons
- **Reader:** Always sets light icons (`isAppearanceLightStatusBars = false`), doesn't restore on dispose

```kotlin
// Shelf - respects theme
DisposableEffect(isDarkTheme) {
    windowInsetsController.isAppearanceLightStatusBars = !isDarkTheme
    onDispose { }
}

// Reader - always light icons for dark background
DisposableEffect(Unit) {
    windowInsetsController.isAppearanceLightStatusBars = false
    onDispose { /* Don't restore - let destination manage */ }
}
```

### 4. Floating Elements During Transition
**Problem:** Floating nav bar stayed visible and didn't animate during shared element transitions.

**Solution:** Changed from floating overlay to proper Column layout, used `animateEnterExit` to fade the top bar during transitions.

## Design Decisions
- **Title style:** Overlaid on cover image with gradient scrim (premium look)
- **Card elevation:** 6dp for depth
- **Background:** `surfaceContainerLowest` for clean, dark surface
- **Top bar:** Minimal - just settings button, fades during transitions

## Animation Taste Note
The `animateEnterExit` fade animation on the top bar during shared element transitions feels polished and premium. This subtle fade-out of UI chrome while the content morphs creates a focused, cinematic feel. This approach aligns with our general animation taste - smooth, purposeful, not distracting.
