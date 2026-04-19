package com.example.newsreader.domain.repository

import com.example.newsreader.domain.model.Article

interface NewsRepositoryInterface {
    suspend fun getArticles(): Result<List<Article>>
    suspend fun getArticleById(id: Int): Result<Article>
}
