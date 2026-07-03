package com.example.newskmp.data.remote

import com.example.newskmp.data.model.NewsResponse

interface ApiService {
    suspend fun getTopStories(section: String): NewsResponse
}