package me.efedaniel.substracker.di

import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Exposes the app's Metro [AppGraph] to the composition.
 *
 * Nullable so that previews (which render without a [me.efedaniel.substracker.SubsTrackerApplication])
 * can still compose screens; consumers must fall back to static content when null.
 */
val LocalAppGraph = staticCompositionLocalOf<AppGraph?> { null }
