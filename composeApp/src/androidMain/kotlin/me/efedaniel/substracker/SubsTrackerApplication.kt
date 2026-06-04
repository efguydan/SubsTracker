package me.efedaniel.substracker

import android.app.Application
import me.efedaniel.substracker.di.AndroidAppGraph
import me.efedaniel.substracker.di.createAndroidAppGraph

class SubsTrackerApplication : Application() {
    val appGraph: AndroidAppGraph by lazy { createAndroidAppGraph(this) }
}
