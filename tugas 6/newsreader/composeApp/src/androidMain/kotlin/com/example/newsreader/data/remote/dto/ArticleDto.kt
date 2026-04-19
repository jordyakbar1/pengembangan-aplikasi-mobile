package com.example.newsreader.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val id: Int,
    val title: String,
    val body: String
)
