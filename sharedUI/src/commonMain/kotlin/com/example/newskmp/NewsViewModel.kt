package com.example.newskmp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newskmp.data.model.Article
import com.example.newskmp.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class NewsUiState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedSection: String = "home"
)

class NewsViewModel(
    private val repository: NewsRepository
) : ViewModel() {

    private val cache = mutableMapOf<String, List<Article>>()

    private val _uiState = MutableStateFlow(NewsUiState())
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        loadNews("home")
    }

    fun loadNews(section: String) {
        if (section == _uiState.value.selectedSection &&
            _uiState.value.articles.isNotEmpty()) return

        cache[section]?.let { cached ->
            _uiState.value = _uiState.value.copy(
                articles = cached,
                selectedSection = section,
                isLoading = false,
                error = null
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null,
                selectedSection = section
            )
            try {
                val articles = repository.getTopStories(section)
                cache[section] = articles
                _uiState.value = _uiState.value.copy(
                    articles = articles,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    error = e.message ?: "Something went wrong",
                    isLoading = false
                )
            }
        }
    }
}