package com.example.newskmp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newskmp.data.model.Article

class AppViewModel : ViewModel() {
    var selectedArticle by mutableStateOf<Article?>(null)
        private set

    fun selectArticle(article: Article) {
        selectedArticle = article
    }

    fun clearArticle() {
        selectedArticle = null
    }
}