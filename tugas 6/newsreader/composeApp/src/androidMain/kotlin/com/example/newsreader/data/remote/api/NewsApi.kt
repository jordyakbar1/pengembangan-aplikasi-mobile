package com.example.newsreader.data.remote.api

import com.example.newsreader.data.remote.dto.ArticleDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class NewsApi(private val client: HttpClient) {

    private val baseUrl = "https://jsonplaceholder.typicode.com"

    suspend fun getArticles(): List<ArticleDto> {
        return client.get("$baseUrl/posts").body()
    }

    suspend fun getArticleById(id: Int): ArticleDto {
        return client.get("$baseUrl/posts/$id").body()
    }
}
