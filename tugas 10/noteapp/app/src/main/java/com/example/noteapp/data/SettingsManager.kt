package com.example.noteapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

enum class SortOrder { BY_DATE, BY_TITLE }
enum class AppTheme { LIGHT, DARK, SYSTEM }

class SettingsManager(private val context: Context) {
    private val SORT_ORDER = stringPreferencesKey("sort_order")
    private val THEME = stringPreferencesKey("theme")

    val sortOrder: Flow<SortOrder> = context.dataStore.data.map { preferences ->
        val orderName = preferences[SORT_ORDER] ?: SortOrder.BY_DATE.name
        try {
            SortOrder.valueOf(orderName)
        } catch (e: Exception) {
            SortOrder.BY_DATE
        }
    }

    val theme: Flow<AppTheme> = context.dataStore.data.map { preferences ->
        val themeName = preferences[THEME] ?: AppTheme.SYSTEM.name
        try {
            AppTheme.valueOf(themeName)
        } catch (e: Exception) {
            AppTheme.SYSTEM
        }
    }

    suspend fun updateSortOrder(order: SortOrder) {
        context.dataStore.edit { preferences ->
            preferences[SORT_ORDER] = order.name
        }
    }

    suspend fun updateTheme(theme: AppTheme) {
        context.dataStore.edit { preferences ->
            preferences[THEME] = theme.name
        }
    }
}
