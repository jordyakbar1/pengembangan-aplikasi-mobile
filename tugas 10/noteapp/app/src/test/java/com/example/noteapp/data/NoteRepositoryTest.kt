package com.example.noteapp.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import app.cash.turbine.test
import com.example.noteapp.db.NoteDatabase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NoteRepositoryTest {
    private lateinit var database: NoteDatabase
    private lateinit var repository: NoteRepository

    @Before
    fun setup() {
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        NoteDatabase.Schema.create(driver)
        database = NoteDatabase(driver)
        repository = NoteRepositoryImpl(database)
    }

    @Test
    fun `insert and get all notes`() = runTest {
        repository.insertNote(null, "Title 1", "Content 1")
        
        // Menggunakan Turbine untuk mengetes Flow
        repository.getAllNotes().test {
            val notes = awaitItem()
            assertEquals(1, notes.size)
            assertEquals("Title 1", notes[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `search notes returns filtered list via flow`() = runTest {
        repository.insertNote(null, "Apple", "Fruit")
        repository.insertNote(null, "Banana", "Fruit")
        
        // Menggunakan Turbine untuk mengetes Flow pencarian
        repository.searchNotes("Apple").test {
            val results = awaitItem()
            assertEquals(1, results.size)
            assertEquals("Apple", results[0].title)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getNoteById returns correct data`() = runTest {
        repository.insertNote(10, "Specific", "Content")
        val note = repository.getNoteById(10)
        assertNotNull(note)
        assertEquals("Specific", note?.title)
    }

    @Test
    fun `deleteNote removes data from database`() = runTest {
        repository.insertNote(1, "Delete Me", "Content")
        repository.deleteNote(1)
        
        repository.getAllNotes().test {
            assertTrue(awaitItem().isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert with same id updates existing note`() = runTest {
        repository.insertNote(1, "Old Title", "Content")
        repository.insertNote(1, "New Title", "Content")
        val note = repository.getNoteById(1)
        assertEquals("New Title", note?.title)
    }
}
