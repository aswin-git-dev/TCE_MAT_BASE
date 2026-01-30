package com.example.matbase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.databinding.ItemGridCardBinding

class DepartmentAdapter(
    private val departments: List<String>,
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<DepartmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemGridCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val department = departments[position]
        holder.bind(department)
        holder.itemView.setOnClickListener { onItemClick(department) }
    }

    override fun getItemCount() = departments.size

    class ViewHolder(private val binding: ItemGridCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(department: String) {
            binding.textView.text = department

        }
    }
}
