package me.efedaniel.substracker.concurrency

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Injected dispatcher abstraction so coroutine contexts are never hardcoded
 * at the use site and can be replaced in tests.
 */
interface AppDispatchers {
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher
    val main: CoroutineDispatcher
}
