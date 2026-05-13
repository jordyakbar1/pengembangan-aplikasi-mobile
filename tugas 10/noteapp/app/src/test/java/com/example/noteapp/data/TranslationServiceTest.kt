package com.example.noteapp.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GenerateContentResponse
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TranslationServiceTest {

    private lateinit var service: TranslationService
    private val mockModel = mockk<GenerativeModel>()

    @Before
    fun setup() {
        service = TranslationService("fake_api_key", mockModel)
    }

    @Test
    fun `translate returns success when model generates text`() = runTest {
        val mockResponse = mockk<GenerateContentResponse>()
        every { mockResponse.text } returns "Halo Dunia"
        coEvery { mockModel.generateContent(any<String>()) } returns mockResponse

        val result = service.translate("Hello World", "Indonesian")

        assertTrue(result.isSuccess)
        assertEquals("Halo Dunia", result.getOrNull())
    }

    @Test
    fun `translate returns failure when model returns blank text`() = runTest {
        val mockResponse = mockk<GenerateContentResponse>()
        every { mockResponse.text } returns ""
        coEvery { mockModel.generateContent(any<String>()) } returns mockResponse

        val result = service.translate("Hello World", "Indonesian")

        assertTrue(result.isFailure)
        assertEquals("AI memberikan respon kosong.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `translate returns failure with 404 hint when model throws 404 exception`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Error 404: Model not found")

        val result = service.translate("Hello World", "Indonesian")

        assertTrue(result.isFailure)
        assertEquals("Error 404: Model tidak ditemukan.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `translate returns failure with 429 hint when model throws 429 exception`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Error 429: Rate limit reached")

        val result = service.translate("Hello World", "Indonesian")

        assertTrue(result.isFailure)
        assertEquals("Terlalu banyak permintaan. Tunggu 1 menit.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `translate returns generic failure for other exceptions`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Some other error")

        val result = service.translate("Hello World", "Indonesian")

        assertTrue(result.isFailure)
        assertEquals("Gagal: Some other error", result.exceptionOrNull()?.message)
    }
}
