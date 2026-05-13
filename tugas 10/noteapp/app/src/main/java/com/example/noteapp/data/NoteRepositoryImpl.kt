package com.example.noteapp.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.db.NoteEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepositoryImpl(
    private val database: NoteDatabase,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NoteRepository {

    override fun getAllNotes(): Flow<List<NoteEntity>> =
        database.noteQueries.selectAll().asFlow().mapToList(ioDispatcher)

    override fun searchNotes(query: String): Flow<List<NoteEntity>> =
        database.noteQueries.searchNotes(query).asFlow().mapToList(ioDispatcher)

    override suspend fun getNoteById(id: Long): NoteEntity? = withContext(ioDispatcher) {
        database.noteQueries.getNoteById(id).executeAsOneOrNull()
    }

    override suspend fun insertNote(id: Long?, title: String, content: String) = withContext(ioDispatcher) {
        database.noteQueries.insertNote(
            id = id,
            title = title,
            content = content,
            createdAt = System.currentTimeMillis()
        )
    }

    override suspend fun deleteNote(id: Long) = withContext(ioDispatcher) {
        database.noteQueries.deleteNote(id)
    }
}
