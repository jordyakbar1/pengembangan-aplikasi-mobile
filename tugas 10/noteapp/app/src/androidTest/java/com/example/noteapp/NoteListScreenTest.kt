package com.example.noteapp

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.noteapp.db.NoteEntity
import com.example.noteapp.screens.NoteListScreen
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class NoteListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: NoteViewModel = mockk(relaxed = true)
    private val uiState = MutableStateFlow(NotesUiState())

    @Test
    fun testEmptyStateDisplaysNoNotesMessage() {
        uiState.value = NotesUiState(notes = emptyList(), isLoading = false)
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = {},
                onSettingsClick = {}
            )
        }

        composeTestRule.onNodeWithText("No notes yet").assertIsDisplayed()
    }

    @Test
    fun testNotesListDisplaysNotes() {
        val notes = listOf(
            NoteEntity(id = 1, title = "Note 1", content = "Content 1", createdAt = 0L),
            NoteEntity(id = 2, title = "Note 2", content = "Content 2", createdAt = 1L)
        )
        uiState.value = NotesUiState(notes = notes, isLoading = false)
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = {},
                onSettingsClick = {}
            )
        }

        composeTestRule.onNodeWithText("Note 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Note 2").assertIsDisplayed()
    }

    @Test
    fun testLoadingStateDisplaysCircularProgressIndicator() {
        uiState.value = NotesUiState(isLoading = true)
        every { viewModel.uiState } returns uiState

        composeTestRule.setContent {
            NoteListScreen(
                viewModel = viewModel,
                onNoteClick = {},
                onAddNoteClick = {},
                onSettingsClick = {}
            )
        }

        // Search for a CircularProgressIndicator by its property (indeterminate progress bar)
        composeTestRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }
}
