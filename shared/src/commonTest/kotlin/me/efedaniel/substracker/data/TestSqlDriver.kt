package me.efedaniel.substracker.data

import app.cash.sqldelight.db.SqlDriver

/** In-memory driver with the schema already created, for repository tests. */
expect fun createInMemorySqlDriver(): SqlDriver
