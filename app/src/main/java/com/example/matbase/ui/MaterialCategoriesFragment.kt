package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matbase.R
import com.example.matbase.databinding.FragmentMaterialCategoriesBinding

class MaterialCategoriesFragment : Fragment() {
    private var _binding: FragmentMaterialCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMaterialCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        val toMaterials = View.OnClickListener {
            findNavController().navigate(R.id.action_nav_material_categories_to_nav_materials)
        }

        binding.btnStudyMaterials.setOnClickListener(toMaterials)
        binding.btnPyqs.setOnClickListener(toMaterials)
        binding.btnLabManuals.setOnClickListener(toMaterials)
        binding.btnSyllabus.setOnClickListener(toMaterials)

        binding.btnGradeCalculator.setOnClickListener {
            findNavController().navigate(R.id.action_nav_material_categories_to_nav_grade_calculator)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}