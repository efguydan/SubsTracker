package me.efedaniel.substracker.di

import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class IosAppGraphTest {
    @Test
    fun graphResolvesAccessors() {
        val graph = createIosAppGraph()

        assertNotNull(graph.appDispatchers)
        assertNotNull(graph.subscriptionRepository)
    }

    @Test
    fun repositoryIsSingletonWithinGraph() {
        val graph = createIosAppGraph()

        assertSame(graph.subscriptionRepository, graph.subscriptionRepository)
    }
}
