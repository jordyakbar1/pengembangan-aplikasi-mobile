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

class SummarizationServiceTest {

    private lateinit var service: SummarizationService
    private val mockModel = mockk<GenerativeModel>()

    @Before
    fun setup() {
        service = SummarizationService("fake_api_key", mockModel)
    }

    @Test
    fun `summarize returns success when model generates content`() = runTest {
        val mockResponse = mockk<GenerateContentResponse>()
        every { mockResponse.text } returns "Summary text"
        coEvery { mockModel.generateContent(any<String>()) } returns mockResponse

        val result = service.summarize("Original text")

        assertTrue(result.isSuccess)
        assertEquals("Summary text", result.getOrNull())
    }

    @Test
    fun `summarize returns failure when model returns null text`() = runTest {
        val mockResponse = mockk<GenerateContentResponse>()
        every { mockResponse.text } returns null
        coEvery { mockModel.generateContent(any<String>()) } returns mockResponse

        val result = service.summarize("Original text")

        assertTrue(result.isFailure)
        assertEquals("AI memberikan respon kosong. Coba tambahkan isi catatan yang lebih panjang.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `summarize returns failure with 404 hint when model throws 404 exception`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Error 404: Not Found")

        val result = service.summarize("Original text")

        assertTrue(result.isFailure)
        assertEquals("Model tidak ditemukan (404).", result.exceptionOrNull()?.message)
    }

    @Test
    fun `summarize returns failure with 429 hint when model throws 429 exception`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Error 429: Too Many Requests")

        val result = service.summarize("Original text")

        assertTrue(result.isFailure)
        assertEquals("Kuota gratis habis. Tunggu 1 menit.", result.exceptionOrNull()?.message)
    }

    @Test
    fun `summarize returns generic failure for other exceptions`() = runTest {
        coEvery { mockModel.generateContent(any<String>()) } throws Exception("Generic error")

        val result = service.summarize("Original text")

        assertTrue(result.isFailure)
        assertEquals("Gagal: Generic error", result.exceptionOrNull()?.message)
    }
}
