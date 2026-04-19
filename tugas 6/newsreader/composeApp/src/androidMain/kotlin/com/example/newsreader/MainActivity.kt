package com.example.newsreader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.newsreader.data.remote.HttpClientFactory
import com.example.newsreader.data.remote.api.NewsApi
import com.example.newsreader.data.repository.NewsRepository
import com.example.newsreader.domain.model.Article
import com.example.newsreader.presentation.screen.detail.DetailScreen
import com.example.newsreader.presentation.screen.news.NewsScreen
import com.example.newsreader.presentation.screen.news.NewsViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = HttpClientFactory.create()
        val api = NewsApi(client)
        val repo = NewsRepository(api)

        setContent {
            val viewModel = remember { NewsViewModel(repo) }
            var selectedArticle by remember { mutableStateOf<Article?>(null) }

            if (selectedArticle == null) {
                NewsScreen(
                    viewModel = viewModel,
                    onArticleClick = { article ->
                        selectedArticle = article
                    }
                )
            } else {
                DetailScreen(
                    article = selectedArticle!!,
                    onBackClick = { selectedArticle = null }
                )
            }
        }
    }
}
