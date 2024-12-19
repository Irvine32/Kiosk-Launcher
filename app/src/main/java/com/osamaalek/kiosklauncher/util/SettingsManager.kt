package com.osamaalek.kiosklauncher.util

import android.content.Context
import android.content.SharedPreferences

object SettingsManager {
    private const val PREF_NAME = "kiosk_settings"
    private const val KEY_AUTO_LAUNCH_APP = "auto_launch_app"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setAutoLaunchApp(context: Context, packageName: String) {
        getPreferences(context).edit().putString(KEY_AUTO_LAUNCH_APP, packageName).apply()
    }

    fun getAutoLaunchApp(context: Context): String? {
        return getPreferences(context).getString(KEY_AUTO_LAUNCH_APP, null)
    }
}
