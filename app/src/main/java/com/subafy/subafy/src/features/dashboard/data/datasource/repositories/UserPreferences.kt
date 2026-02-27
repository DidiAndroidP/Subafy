package com.subafy.subafy.src.features.dashboard.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val nicknameKey = stringPreferencesKey("user_nickname")
    private val avatarKey = stringPreferencesKey("user_avatar_url")

    val nickname: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[nicknameKey] ?: ""
    }

    val avatarUrl: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[avatarKey]
    }

    suspend fun saveUser(nickname: String, avatarUrl: String?) {
        context.dataStore.edit { prefs ->
            prefs[nicknameKey] = nickname
            if (avatarUrl != null) {
                prefs[avatarKey] = avatarUrl
            }
        }
    }
}