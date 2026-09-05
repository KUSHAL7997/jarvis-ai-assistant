package com.kushal.jarvis.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SharedPrefsManager(context: Context) {

    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "jarvis_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Google Gemini API Key
    fun setGeminiApiKey(apiKey: String) {
        encryptedSharedPreferences.edit().putString("gemini_api_key", apiKey).apply()
    }

    fun getGeminiApiKey(): String? {
        return encryptedSharedPreferences.getString("gemini_api_key", null)
    }

    // Commander Name (User Identity)
    fun setCommanderName(name: String) {
        encryptedSharedPreferences.edit().putString("commander_name", name).apply()
    }

    fun getCommanderName(): String? {
        return encryptedSharedPreferences.getString("commander_name", null)
    }

    // First Launch Flag
    fun setFirstLaunchCompleted(completed: Boolean) {
        encryptedSharedPreferences.edit().putBoolean("first_launch_completed", completed).apply()
    }

    fun isFirstLaunchCompleted(): Boolean {
        return encryptedSharedPreferences.getBoolean("first_launch_completed", false)
    }

    // Permissions Accepted Flag
    fun setPermissionsAccepted(accepted: Boolean) {
        encryptedSharedPreferences.edit().putBoolean("permissions_accepted", accepted).apply()
    }

    fun isPermissionsAccepted(): Boolean {
        return encryptedSharedPreferences.getBoolean("permissions_accepted", false)
    }

    // Clear all data
    fun clearAll() {
        encryptedSharedPreferences.edit().clear().apply()
    }
}
