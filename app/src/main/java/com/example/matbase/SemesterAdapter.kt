package com.example.matbase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.databinding.ItemGridCardBinding

class SemesterAdapter(
    private val semesters: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<SemesterAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val semester = semesters[position]
        holder.bind(semester)
        holder.itemView.setOnClickListener { onItemClick(semester) }
    }

    override fun getItemCount() = semesters.size

    class ViewHolder(private val binding: ItemGridCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(semester: String) {
            binding.textView.text = semester
        }
    }
}
