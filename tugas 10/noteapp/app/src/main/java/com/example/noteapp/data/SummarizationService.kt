package com.example.noteapp.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SummarizationService(
    private val apiKey: String,
    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey
    )
) {

    suspend fun summarize(text: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val response = model.generateContent("Ringkas teks catatan ini menjadi poin-poin singkat dan jelas: $text")
            val result = response.text
            
            if (!result.isNullOrBlank()) {
                Result.success(result.trim())
            } else {
                Result.failure(Exception("AI memberikan respon kosong. Coba tambahkan isi catatan yang lebih panjang."))
            }
        } catch (e: Exception) {
            val msg = e.message ?: ""
            val errorHint = when {
                msg.contains("404") -> "Model tidak ditemukan (404)."
                msg.contains("429") -> "Kuota gratis habis. Tunggu 1 menit."
                else -> "Gagal: $msg"
            }
            Result.failure(Exception(errorHint))
        }
    }
}
