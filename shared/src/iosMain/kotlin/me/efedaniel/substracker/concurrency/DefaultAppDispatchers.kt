package me.efedaniel.substracker.concurrency

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class DefaultAppDispatchers : AppDispatchers {
    // No dedicated IO dispatcher on Kotlin/Native; Default is the blocking-work pool.
    override val io: CoroutineDispatcher = Dispatchers.Default
    override val default: CoroutineDispatcher = Dispatchers.Default
    override val main: CoroutineDispatcher = Dispatchers.Main
}
