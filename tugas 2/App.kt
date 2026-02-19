package com.example.myapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


@Composable
fun App() {

    // Repository
    val repository = remember { NewsRepository() }

    // StateFlow manager
    val newsState = remember { NewsState() }

    // jumlah dibaca
    val readCount by newsState.readCount.collectAsState()

    // list berita
    val newsList = remember { mutableStateListOf<News>() }

    // detail berita dipilih
    var detailText by remember { mutableStateOf("Klik berita untuk lihat detail") }

    val scope = rememberCoroutineScope()



    // FLOW → ambil berita tiap 2 detik
    LaunchedEffect(Unit) {

        repository.newsFlow()

            // FILTER kategori Technology
            .collect { news ->

                if (news.category == "Technology") {

                    newsList.add(news)

                }

            }

    }



    MaterialTheme {

        Column(

            modifier = Modifier

                .fillMaxSize()

                .safeContentPadding()

                .padding(16.dp)

        ) {

            Text(

                text = "NEWS FEED SIMULATOR",

                style = MaterialTheme.typography.headlineSmall

            )


            Text(

                text = "Jumlah Dibaca: $readCount",

                modifier = Modifier.padding(bottom = 16.dp)

            )



            // LIST BERITA

            newsList.forEach { news ->

                Text(

                    text = "📰 ${news.title} (${news.category})",

                    modifier = Modifier

                        .padding(8.dp)

                        .clickable {

                            scope.launch {

                                // Coroutine async ambil detail

                                detailText = repository.getDetail(news)

                                newsState.markRead()

                            }

                        }

                )

            }



            Text(

                text = "\nDETAIL BERITA:",

                style = MaterialTheme.typography.titleMedium

            )


            Text(

                text = detailText,

                modifier = Modifier.padding(top = 8.dp)

            )

        }

    }

}