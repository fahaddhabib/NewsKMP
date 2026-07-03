package com.example.newskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform