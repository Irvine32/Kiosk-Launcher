package com.osamaalek.kiosklauncher.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.osamaalek.kiosklauncher.R
import com.osamaalek.kiosklauncher.model.AppInfo

class AppSelectionAdapter(
    private val apps: List<AppInfo>,
    private val onAppSelected: (AppInfo) -> Unit
) : RecyclerView.Adapter<AppSelectionAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val appIcon: ImageView = view.findViewById(R.id.imageView_app_icon)
        val appName: TextView = view.findViewById(R.id.textView_app_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_app_selection, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.appIcon.setImageDrawable(app.icon)
        holder.appName.text = app.label
        holder.itemView.setOnClickListener { onAppSelected(app) }
    }

    override fun getItemCount() = apps.size
}
