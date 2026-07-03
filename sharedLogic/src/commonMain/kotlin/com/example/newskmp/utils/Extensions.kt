package com.example.newskmp.utils

import com.example.newskmp.data.model.Article

fun Article.getThumbnailUrl(): String? {
    return multimedia?.find { it.format == "threeByTwoSmallAt2X" }?.url
}

fun Article.getLargeImageUrl(): String? {
    return multimedia?.find { it.format == "Super Jumbo" }?.url
}