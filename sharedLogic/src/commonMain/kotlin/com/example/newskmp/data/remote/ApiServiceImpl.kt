package com.example.newskmp.data.remote

import com.example.newskmp.data.model.NewsResponse
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class ApiServiceImpl : ApiService {

    private val client = createHttpClient()
    private val baseUrl = "https://api.nytimes.com/svc/topstories/v2"
    private val apiKey = "P4NjsCL4dMqGPogicJAxvkamd1UWrTGAHtkd1wHNlk34Ml0n"

    override suspend fun getTopStories(section: String): NewsResponse {
        return client.get("$baseUrl/$section.json"){
            parameter("api-key", apiKey)
        }.body()
    }
}