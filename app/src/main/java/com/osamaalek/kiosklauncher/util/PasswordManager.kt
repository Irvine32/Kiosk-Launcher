package com.osamaalek.kiosklauncher.util

import android.content.Context
import android.content.SharedPreferences

object PasswordManager {
    private const val PREF_NAME = "kiosk_settings"
    private const val KEY_PASSWORD = "exit_password"
    private const val DEFAULT_PASSWORD = "1234" // Default password

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setPassword(context: Context, password: String) {
        getPreferences(context).edit().putString(KEY_PASSWORD, password).apply()
    }

    fun getPassword(context: Context): String {
        return getPreferences(context).getString(KEY_PASSWORD, DEFAULT_PASSWORD) ?: DEFAULT_PASSWORD
    }

    fun checkPassword(context: Context, inputPassword: String): Boolean {
        val storedPassword = getPassword(context)
        return inputPassword == storedPassword
    }
}
