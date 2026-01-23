package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.R
import com.example.matbase.databinding.FragmentMaterialsBinding

class MaterialsFragment : Fragment() {

    private var _binding: FragmentMaterialsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMaterialsBinding.inflate(inflater, container, false)
        
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }

        val materials = listOf("Study Materials", "PYQs", "Lab Manuals", "Syllabus", "Grade calculator")
        binding.recyclerviewMaterials.adapter = MaterialsAdapter(materials)
        
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class MaterialsAdapter(private val items: List<String>) : 
        RecyclerView.Adapter<MaterialsAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val textView: TextView = view.findViewById(R.id.text_view)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grid_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = items[position]
        }

        override fun getItemCount() = items.size
    }
}