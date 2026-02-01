# Mongoose TODO

## In Progress

## Backlog

### Shelf to Reader Transition Animation
Add a container transform animation when navigating from the manga shelf to the reader screen. The manga cover card should expand/zoom into the reader as if opening a book.

**Approach:**
- Use Compose shared element transitions (`SharedTransitionLayout`, `sharedElement` modifier)
- Share the manga cover image between `MangaCoverCard` and the reader screen
- Consider using `AnimatedContent` or navigation animation specs

**Files to modify:**
- `AppNavigation.kt` - wrap with `SharedTransitionLayout`
- `ShelfScreen.kt` - add shared element key to cover image
- `ReaderScreen.kt` - add matching shared element key

---

## Completed

