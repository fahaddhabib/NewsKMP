package com.example.newskmp.data.repository

import com.example.newskmp.data.model.Article
import com.example.newskmp.data.remote.ApiService
import com.example.newskmp.data.remote.ApiServiceImpl

class NewsRepositoryImpl(
    private val apiService: ApiService
): NewsRepository {

    override suspend fun getTopStories(section: String): List<Article> {
        return apiService.getTopStories(section).results ?: emptyList()
    }
}