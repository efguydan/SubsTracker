package me.efedaniel.substracker.data.db

import app.cash.sqldelight.db.SqlDriver

/** Platform-specific [SqlDriver] creation; the schema and queries are shared. */
expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DatabaseDriverFactory): SubsTrackerDatabase = SubsTrackerDatabase(driverFactory.createDriver())
