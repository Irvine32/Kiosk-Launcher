package com.osamaalek.kiosklauncher.ui

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.util.KioskUtil

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set full screen and hide system UI
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        
        // Disable recent apps button
        window.addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        
        setContentView(R.layout.activity_main)

        KioskUtil.startKioskMode(this)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) is AppsListFragment) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, HomeFragment()).commit()
        }
    }
}