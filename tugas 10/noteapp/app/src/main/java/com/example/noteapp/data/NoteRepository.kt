package com.example.noteapp.data

import com.example.noteapp.db.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    fun searchNotes(query: String): Flow<List<NoteEntity>>
    suspend fun getNoteById(id: Long): NoteEntity?
    suspend fun insertNote(id: Long?, title: String, content: String)
    suspend fun deleteNote(id: Long)
}
