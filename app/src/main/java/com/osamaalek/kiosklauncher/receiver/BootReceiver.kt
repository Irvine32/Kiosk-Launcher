package com.osamaalek.kiosklauncher.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.osamaalek.kiosklauncher.util.AppLaunchUtil
import com.osamaalek.kiosklauncher.util.SettingsManager

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val autoLaunchPackage = SettingsManager.getAutoLaunchApp(context)
            if (autoLaunchPackage != null) {
                AppLaunchUtil.launchAppFullScreen(context, autoLaunchPackage)
            }
        }
    }
}
