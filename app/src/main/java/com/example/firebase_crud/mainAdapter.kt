package com.example.firebase_crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class mainAdapter(private val taskList: List<mainModel>) : RecyclerView.Adapter<mainAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.name)
        val email:TextView = itemView.findViewById(R.id.email)
        val subject:TextView = itemView.findViewById(R.id.subject)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  taskList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.name.text = task.name
        holder.email.text = task.email
        holder.subject.text = task.subject
    }
}