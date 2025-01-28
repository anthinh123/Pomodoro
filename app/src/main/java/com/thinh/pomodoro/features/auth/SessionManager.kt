package com.thinh.pomodoro.features.auth

import android.content.Context

class SessionManager(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("PomodoroPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveAuthToken(token: String) {
        editor.putString("authToken", token)
        editor.apply()
    }

    fun getAuthToken(): String? {
        return sharedPreferences.getString("authToken", null)
    }

    fun clearSession() {
        editor.clear()
        editor.apply()
    }

}
