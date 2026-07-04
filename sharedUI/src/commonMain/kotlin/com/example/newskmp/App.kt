package com.example.newskmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            val appViewModel = koinViewModel<AppViewModel>(

            )

            if (appViewModel.selectedArticle != null) {
                ArticleDetailScreen(
                    article = appViewModel.selectedArticle!!,
                    onBackClick = { appViewModel.clearArticle() }
                )
            } else {
                NewsListScreen(
                    onArticleClick = { article ->
                        appViewModel.selectArticle(article)
                    }
                )
            }
        }
    }
}