package com.example.noteapp.data

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TranslationService(
    private val apiKey: String,
    private val model: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash", // Menggunakan model yang stabil
        apiKey = apiKey
    )
) {

    suspend fun translate(text: String, targetLanguage: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = "Translate this text to $targetLanguage. Provide only the translated text: $text"
            
            val response = model.generateContent(prompt)
            val translatedText = response.text
            
            if (!translatedText.isNullOrBlank()) {
                Result.success(translatedText.trim())
            } else {
                Result.failure(Exception("AI memberikan respon kosong."))
            }
        } catch (e: Exception) {
            val msg = e.message ?: ""
            val errorHint = when {
                msg.contains("404") -> "Error 404: Model tidak ditemukan."
                msg.contains("429") -> "Terlalu banyak permintaan. Tunggu 1 menit."
                else -> "Gagal: $msg"
            }
            Result.failure(Exception(errorHint))
        }
    }
}
