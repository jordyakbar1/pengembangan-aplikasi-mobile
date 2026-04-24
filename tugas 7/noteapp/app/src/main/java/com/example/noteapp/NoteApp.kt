package com.example.noteapp

import android.app.Application
import com.example.noteapp.data.DatabaseDriverFactory
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.db.NoteDatabase

class NoteApp : Application() {
    lateinit var database: NoteDatabase
    lateinit var settingsManager: SettingsManager

    override fun onCreate() {
        super.onCreate()
        val driver = DatabaseDriverFactory(this).createDriver()
        database = NoteDatabase(driver)
        settingsManager = SettingsManager(this)
    }
}
