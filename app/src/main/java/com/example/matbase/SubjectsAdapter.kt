package com.example.matbase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.databinding.ItemGridCardBinding

class SubjectsAdapter(
    private val subjects: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<SubjectsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subject = subjects[position]
        holder.bind(subject)
        holder.itemView.setOnClickListener { onItemClick(subject) }
    }

    override fun getItemCount() = subjects.size

    class ViewHolder(private val binding: ItemGridCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(subject: String) {
            binding.textView.text = subject
        }
    }
}
