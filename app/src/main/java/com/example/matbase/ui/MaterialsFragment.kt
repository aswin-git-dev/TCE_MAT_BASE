package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.R
import com.example.matbase.data.Material
import com.example.matbase.databinding.DialogAddMaterialBinding
import com.example.matbase.databinding.FragmentMaterialsBinding

class MaterialsFragment : Fragment() {

    private var _binding: FragmentMaterialsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MaterialsViewModel by viewModels()
    private lateinit var adapter: MaterialsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaterialsBinding.inflate(inflater, container, false)

        setupRecyclerView()
        observeMaterials()

        binding.fabAddMaterial.setOnClickListener {
            showAddMaterialDialog()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        adapter = MaterialsAdapter { material ->
            Toast.makeText(requireContext(), "Viewing: ${material.name}", Toast.LENGTH_SHORT).show()
        }
        binding.recyclerviewMaterials.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewMaterials.adapter = adapter
    }

    private fun observeMaterials() {
        viewModel.allMaterials.observe(viewLifecycleOwner) { materials ->
            adapter.submitList(materials)
        }
    }

    private fun showAddMaterialDialog() {
        val dialogBinding = DialogAddMaterialBinding.inflate(layoutInflater)
        
        AlertDialog.Builder(requireContext())
            .setTitle("Add Material")
            .setView(dialogBinding.root)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.etMaterialName.text.toString()
                val subject = dialogBinding.etMaterialSubject.text.toString()
                val description = dialogBinding.etMaterialDescription.text.toString()

                if (name.isNotBlank() && subject.isNotBlank()) {
                    val newMaterial = Material(
                        name = name,
                        subject = subject,
                        description = description
                    )
                    viewModel.insert(newMaterial)
                } else {
                    Toast.makeText(requireContext(), "Please fill in Name and Subject", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MaterialsAdapter(private val onViewClick: (Material) -> Unit) : 
        RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

        private var items = listOf<Material>()

        fun submitList(newList: List<Material>) {
            items = newList
            notifyDataSetChanged()
        }

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName: TextView = view.findViewById(R.id.tv_material_name)
            val tvSubject: TextView = view.findViewById(R.id.tv_material_subject)
            val btnView: Button = view.findViewById(R.id.btn_view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_material, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val material = items[position]
            holder.tvName.text = material.name
            holder.tvSubject.text = material.subject
            holder.btnView.setOnClickListener { onViewClick(material) }
        }

        override fun getItemCount() = items.size
    }
}