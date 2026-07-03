package com.example.newskmp.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    @SerialName("copyright") val copyright: String = "",
    @SerialName("last_updated") val lastUpdated: String = "",
    @SerialName("num_results") val numResults: Int = 0,
    @SerialName("results") val results: List<Article>? = null,
    @SerialName("section") val section: String = "",
    @SerialName("status") val status: String = ""
)