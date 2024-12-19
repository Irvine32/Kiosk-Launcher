package com.osamaalek.kiosklauncher.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.adapter.AppSelectionAdapter
import com.osamaalek.kiosklauncher.util.AppsUtil
import com.osamaalek.kiosklauncher.util.KioskUtil
import com.osamaalek.kiosklauncher.util.PasswordManager
import com.osamaalek.kiosklauncher.util.SettingsManager

class HomeFragment : Fragment() {

    private lateinit var fabApps: FloatingActionButton
    private lateinit var imageButtonExit: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        fabApps = view.findViewById(R.id.fab_apps)
        imageButtonExit = view.findViewById(R.id.imageButton_exit)

        fabApps.setOnClickListener {
            val intent = Intent(requireContext(), AppsActivity::class.java)
            startActivity(intent)
        }

        imageButtonExit.setOnClickListener {
            showPasswordDialog()
        }

        return view
    }

    private fun showPasswordDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_password, null)
        val passwordEditText = dialogView.findViewById<EditText>(R.id.editText_password)

        AlertDialog.Builder(requireContext())
            .setTitle("Enter Password")
            .setView(dialogView)
            .setPositiveButton("OK") { _, _ ->
                val password = passwordEditText.text.toString()
                if (PasswordManager.checkPassword(requireContext(), password)) {
                    showAppSelectionDialog()
                } else {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Error")
                        .setMessage("Wrong password!")
                        .setPositiveButton("OK", null)
                        .show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showAppSelectionDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_app_selection, null)
        val recyclerView = dialogView.findViewById<RecyclerView>(R.id.recyclerView_apps)
        
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val apps = AppsUtil.getAllApps(requireContext())
        val adapter = AppSelectionAdapter(apps) { selectedApp ->
            SettingsManager.setAutoLaunchApp(requireContext(), selectedApp.packageName.toString())
            KioskUtil.stopKioskMode(requireActivity())
        }
        recyclerView.adapter = adapter

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Cancel", null)
            .show()
    }
}