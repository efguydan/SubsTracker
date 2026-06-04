package me.efedaniel.substracker.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.efedaniel.substracker.data.db.SubsTrackerDatabase

actual fun createInMemorySqlDriver(): SqlDriver =
    JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).also { driver ->
        SubsTrackerDatabase.Schema.create(driver)
    }
