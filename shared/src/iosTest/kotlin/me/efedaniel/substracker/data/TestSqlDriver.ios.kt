package me.efedaniel.substracker.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.inMemoryDriver
import me.efedaniel.substracker.data.db.SubsTrackerDatabase

actual fun createInMemorySqlDriver(): SqlDriver = inMemoryDriver(SubsTrackerDatabase.Schema)
