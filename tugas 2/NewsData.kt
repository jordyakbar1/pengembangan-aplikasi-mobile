package com.example.myapplication

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



data class News(

    val id: Int,
    val title: String,
    val category: String,
    val content: String

)



class NewsRepository {

    private val categories = listOf(

        "Technology",
        "Sports",
        "Entertainment"

    )


    fun newsFlow(): Flow<News> = flow {

        var id = 1

        while (true) {

            delay(2000)

            val category = categories.random()

            emit(

                News(

                    id,

                    "Breaking News $id",

                    category,

                    "Full content Breaking News $id"

                )

            )

            id++

        }

    }



    suspend fun getDetail(news: News): String {

        delay(1000)

        return news.content

    }

}




class NewsState {

    private val _readCount = MutableStateFlow(0)

    val readCount: StateFlow<Int> = _readCount


    fun markRead() {

        _readCount.value++

    }

}
