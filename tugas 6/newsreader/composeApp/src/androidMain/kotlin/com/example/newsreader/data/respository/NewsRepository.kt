package com.example.newsreader.data.repository

import com.example.newsreader.data.mapper.toDomain
import com.example.newsreader.data.remote.api.NewsApi
import com.example.newsreader.domain.model.Article
import com.example.newsreader.domain.repository.NewsRepositoryInterface

class NewsRepository(
    private val api: NewsApi
) : NewsRepositoryInterface {

    override suspend fun getArticles(): Result<List<Article>> {
        return try {
            Result.success(api.getArticles().map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getArticleById(id: Int): Result<Article> {
        return try {
            Result.success(api.getArticleById(id).toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
