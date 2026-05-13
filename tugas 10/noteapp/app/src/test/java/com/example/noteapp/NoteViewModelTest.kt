package com.example.noteapp

import app.cash.turbine.test
import com.example.noteapp.data.AppTheme
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.data.SortOrder
import com.example.noteapp.data.SummarizationService
import com.example.noteapp.db.NoteEntity
import com.example.noteapp.platform.DeviceInfo
import com.example.noteapp.platform.NetworkMonitor
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewModelTest {

    private lateinit var viewModel: NoteViewModel
    private val repository: NoteRepository = mockk(relaxed = true)
    private val settingsManager: SettingsManager = mockk(relaxed = true)
    private val networkMonitor: NetworkMonitor = mockk(relaxed = true)
    private val summarizationService: SummarizationService = mockk(relaxed = true)
    private val deviceInfo: DeviceInfo = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mocking default flows
        every { networkMonitor.isOnline } returns MutableStateFlow(true)
        every { settingsManager.theme } returns flowOf(AppTheme.SYSTEM)
        every { settingsManager.sortOrder } returns flowOf(SortOrder.BY_DATE)
        every { repository.getAllNotes() } returns flowOf(emptyList())
        
        viewModel = NoteViewModel(
            repository,
            settingsManager,
            networkMonitor,
            summarizationService,
            deviceInfo
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state has correct default values`() = runTest {
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(true, state.isOnline)
            assertEquals(SortOrder.BY_DATE, state.sortOrder)
        }
    }

    @Test
    fun `onSearchQueryChange updates searchQuery in state`() = runTest {
        viewModel.onSearchQueryChange("test query")
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("test query", state.searchQuery)
        }
    }

    @Test
    fun `addNote calls repository insertNote`() = runTest {
        viewModel.addNote("Title", "Content")
        coVerify { repository.insertNote(null, "Title", "Content") }
    }

    @Test
    fun `updateNote calls repository insertNote with id`() = runTest {
        viewModel.updateNote(1L, "Updated Title", "Updated Content")
        coVerify { repository.insertNote(1L, "Updated Title", "Updated Content") }
    }

    @Test
    fun `deleteNote calls repository deleteNote`() = runTest {
        viewModel.deleteNote(1L)
        coVerify { repository.deleteNote(1L) }
    }

    @Test
    fun `updateSortOrder calls settingsManager`() = runTest {
        viewModel.updateSortOrder(SortOrder.BY_TITLE)
        coVerify { settingsManager.updateSortOrder(SortOrder.BY_TITLE) }
    }

    @Test
    fun `updateTheme calls settingsManager`() = runTest {
        viewModel.updateTheme(AppTheme.DARK)
        coVerify { settingsManager.updateTheme(AppTheme.DARK) }
    }

    @Test
    fun `summarizeNote success updates state and triggers callback`() = runTest {
        val summary = "A short summary"
        coEvery { summarizationService.summarize(any()) } returns Result.success(summary)
        
        var receivedResult = ""
        viewModel.summarizeNote("long content") { receivedResult = it }
        
        assertEquals(summary, receivedResult)
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(false, state.isSummarizing)
            assertEquals(null, state.summarizationError)
        }
    }

    @Test
    fun `summarizeNote failure updates error state`() = runTest {
        coEvery { summarizationService.summarize(any()) } returns Result.failure(Exception("AI Error"))
        
        viewModel.summarizeNote("content") { }
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("AI Error", state.summarizationError)
        }
    }

    @Test
    fun `clearSummarizationError resets error state`() = runTest {
        // Set error first
        coEvery { summarizationService.summarize(any()) } returns Result.failure(Exception("Error"))
        viewModel.summarizeNote("content") { }
        
        viewModel.clearSummarizationError()
        
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(null, state.summarizationError)
        }
    }
}
