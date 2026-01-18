package me.efedaniel.substracker

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform