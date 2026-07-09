package com.example.newskmp.data.repository

import com.example.newskmp.data.database.ArticleDao
import com.example.newskmp.data.model.Article
import com.example.newskmp.data.remote.ApiService

class NewsRepositoryImpl(
    private val apiService: ApiService,
    private val articleDao: ArticleDao
) : NewsRepository {

    override suspend fun getTopStories(section: String): List<Article> {
        return try {
            val articles = apiService.getTopStories(section).results ?: emptyList()
            articleDao.insertArticles(articles, section)
            articles
        } catch (e: Exception) {
            val cached = articleDao.getArticlesBySection(section)
            if (cached.isNotEmpty()) cached
            else throw e
        }
    }
}