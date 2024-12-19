package com.osamaalek.kiosklauncher.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.WindowManager

object AppLaunchUtil {
    fun launchAppFullScreen(context: Context, packageName: String) {
        try {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                
                // If context is an Activity, set it to full screen
                if (context is Activity) {
                    context.window.apply {
                        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                or View.SYSTEM_UI_FLAG_FULLSCREEN
                                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
                        addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
                        addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                    }
                }
                
                context.startActivity(launchIntent)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
