package com.example.newsreader.data.mapper

import com.example.newsreader.data.remote.dto.ArticleDto
import com.example.newsreader.domain.model.Article

fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        title = title,
        description = body,
        imageUrl = "https://picsum.photos/200/300?random=$id"
    )
}
