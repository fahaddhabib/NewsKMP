package com.example.newskmp.data.database

import com.example.newskmp.data.model.Article
import com.example.newskmp.data.model.Multimedia
import com.example.newskmp.database.ArticleEntity
import com.example.newskmp.database.NewsDatabase
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ArticleDao(driverFactory: DatabaseDriverFactory) {

    private val database = NewsDatabase(driverFactory.createDriver())
    private val queries = database.articleEntityQueries

    fun insertArticles(articles: List<Article>, section: String) {
        queries.transaction {
            queries.deleteArticlesBySection(section)
            articles.forEach { article ->
                queries.insertArticle(
                    url = article.url,
                    title = article.title,
                    abstract_ = article.abstract,
                    byline = article.byline,
                    section = article.section,
                    subsection = article.subsection,
                    published_date = article.publishedDate,
                    kicker = article.kicker,
                    thumbnail_url = article.multimedia?.find { it.format == "threeByTwoSmallAt2X" }?.url,
                    large_image_url = article.multimedia?.find { it.format == "Super Jumbo" }?.url,
                    item_type = article.itemType
                )
            }
        }
    }

    fun getArticlesBySection(section: String): List<Article> {
        return queries.selectArticlesBySection(section).executeAsList().map { it.toArticle() }
    }

    fun getAllArticles(): List<Article> {
        return queries.selectAllArticles().executeAsList().map { it.toArticle() }
    }
}

private fun ArticleEntity.toArticle(): Article {
    return Article(
        url = url,
        title = title,
        abstract = abstract_,
        byline = byline,
        section = section,
        subsection = subsection,
        publishedDate = published_date,
        kicker = kicker,
        itemType = item_type,
        multimedia = null,
        createdDate = "",
        updatedDate = "",
        uri = "",
        shortUrl = "",
        desFacet = emptyList(),
        geoFacet = emptyList(),
        orgFacet = emptyList(),
        perFacet = emptyList(),
        materialTypeFacet = ""
    )
}