package com.example.noteapp.di

import com.example.noteapp.BuildConfig
import com.example.noteapp.NoteViewModel
import com.example.noteapp.data.DatabaseDriverFactory
import com.example.noteapp.data.NoteRepository
import com.example.noteapp.data.NoteRepositoryImpl
import com.example.noteapp.data.SettingsManager
import com.example.noteapp.data.SummarizationService
import com.example.noteapp.db.NoteDatabase
import com.example.noteapp.platform.AndroidDeviceInfo
import com.example.noteapp.platform.AndroidNetworkMonitor
import com.example.noteapp.platform.DeviceInfo
import com.example.noteapp.platform.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single { DatabaseDriverFactory(androidContext()).createDriver() }
    single { NoteDatabase(get()) }
    single { SettingsManager(androidContext()) }
    single<NetworkMonitor> { AndroidNetworkMonitor(androidContext()) }
    single<DeviceInfo> { AndroidDeviceInfo() }
    single { SummarizationService(BuildConfig.GEMINI_API_KEY) }
    single<NoteRepository> { NoteRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { NoteViewModel(get(), get(), get(), get(), get()) }
}
