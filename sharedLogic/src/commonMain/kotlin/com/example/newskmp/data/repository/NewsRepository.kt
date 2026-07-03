package com.example.newskmp.data.repository

import com.example.newskmp.data.model.Article

interface NewsRepository {
    suspend fun getTopStories(section: String): List<Article>
}