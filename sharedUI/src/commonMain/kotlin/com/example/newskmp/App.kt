package com.example.newskmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.newskmp.data.model.Article

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            var selectedArticle by remember { mutableStateOf<Article?>(null) }

            if (selectedArticle != null) {
                ArticleDetailScreen(
                    article = selectedArticle!!,
                    onBackClick = { selectedArticle = null }
                )
            } else {
                NewsListScreen(
                    onArticleClick = { article ->
                        selectedArticle = article
                    }
                )
            }
        }
    }
}