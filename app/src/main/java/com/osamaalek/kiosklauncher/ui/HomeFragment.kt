package com.osamaalek.kiosklauncher.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
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
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, AppsListFragment()).commit()
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
                    showSettingsDialog()
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

    private fun showSettingsDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_settings, null)
        
        val selectAppButton = dialogView.findViewById<Button>(R.id.button_select_app)
        val changePasswordButton = dialogView.findViewById<Button>(R.id.button_change_password)
        val exitKioskButton = dialogView.findViewById<Button>(R.id.button_exit_kiosk)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        selectAppButton.setOnClickListener {
            dialog.dismiss()
            showAppSelectionDialog()
        }

        changePasswordButton.setOnClickListener {
            dialog.dismiss()
            showChangePasswordDialog()
        }

        exitKioskButton.setOnClickListener {
            dialog.dismiss()
            KioskUtil.stopKioskMode(requireActivity())
        }

        dialog.show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_change_password, null)
        
        val newPasswordEdit = dialogView.findViewById<EditText>(R.id.editText_new_password)
        val confirmPasswordEdit = dialogView.findViewById<EditText>(R.id.editText_confirm_password)

        AlertDialog.Builder(requireContext())
            .setTitle("Change Password")
            .setView(dialogView)
            .setPositiveButton("Save") { _, _ ->
                val newPassword = newPasswordEdit.text.toString()
                val confirmPassword = confirmPasswordEdit.text.toString()

                if (newPassword.isEmpty()) {
                    Toast.makeText(requireContext(), 
                        "Password cannot be empty", 
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                if (newPassword != confirmPassword) {
                    Toast.makeText(requireContext(), 
                        "Passwords do not match", 
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                PasswordManager.setPassword(requireContext(), newPassword)
                Toast.makeText(requireContext(), 
                    "Password changed successfully", 
                    Toast.LENGTH_SHORT).show()
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
        
        // Get currently selected app
        val currentApp = SettingsManager.getAutoLaunchApp(requireContext())
        
        val adapter = AppSelectionAdapter(apps) { selectedApp ->
            SettingsManager.setAutoLaunchApp(requireContext(), selectedApp.packageName.toString())
            Toast.makeText(requireContext(), 
                "Selected ${selectedApp.label} as auto-launch app", 
                Toast.LENGTH_SHORT).show()
        }
        recyclerView.adapter = adapter

        AlertDialog.Builder(requireContext())
            .setTitle("Select Auto-launch App")
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .show()
    }
}