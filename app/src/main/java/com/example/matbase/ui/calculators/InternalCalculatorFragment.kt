package com.example.matbase.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matbase.databinding.FragmentInternalCalculatorBinding

class InternalCalculatorFragment : Fragment() {

    private var _binding: FragmentInternalCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInternalCalculatorBinding.inflate(inflater, container, false)

        setupDropdown()
        
        binding.btnCalculateInternal.setOnClickListener {
            calculateInternal()
        }

        return binding.root
    }

    private fun setupDropdown() {
        val types = arrayOf("Theory", "Practical", "Theory cum Practical")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, types)
        binding.actvSubjectType.setAdapter(adapter)

        binding.actvSubjectType.setOnItemClickListener { _, _, position, _ ->
            when (position) {
                0 -> { // Theory
                    binding.layoutTheory.visibility = View.VISIBLE
                    binding.layoutGeneric.visibility = View.GONE
                }
                1, 2 -> { // Practical or TCP
                    binding.layoutTheory.visibility = View.GONE
                    binding.layoutGeneric.visibility = View.VISIBLE
                    binding.etTotal.hint = if (position == 1) "Total Marks (to scale to 60)" else "Total Marks (to scale to 50)"
                }
            }
            binding.tvInternalResult.text = ""
        }
    }

    private fun calculateInternal() {
        val type = binding.actvSubjectType.text.toString()
        if (type.isEmpty()) {
            Toast.makeText(requireContext(), "Select subject type", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            when (type) {
                "Theory" -> {
                    val cat1 = binding.etCat1.text.toString().toDoubleOrNull() ?: 0.0
                    val ass1 = binding.etAssign1.text.toString().toDoubleOrNull() ?: 0.0
                    val cat2 = binding.etCat2.text.toString().toDoubleOrNull() ?: 0.0
                    val ass2 = binding.etAssign2.text.toString().toDoubleOrNull() ?: 0.0
                    
                    val total200 = cat1 + ass1 + cat2 + ass2
                    val internal40 = (total200 / 200.0) * 40.0
                    binding.tvInternalResult.text = String.format("Internal Mark: %.2f / 40", internal40)
                }
                "Practical" -> {
                    val obtained = binding.etObtained.text.toString().toDoubleOrNull() ?: 0.0
                    val total = binding.etTotal.text.toString().toDoubleOrNull() ?: 1.0
                    val internal60 = (obtained / total) * 60.0
                    binding.tvInternalResult.text = String.format("Internal Mark: %.2f / 60", internal60)
                }
                "Theory cum Practical" -> {
                    val obtained = binding.etObtained.text.toString().toDoubleOrNull() ?: 0.0
                    val total = binding.etTotal.text.toString().toDoubleOrNull() ?: 1.0
                    val internal50 = (obtained / total) * 50.0
                    binding.tvInternalResult.text = String.format("Internal Mark: %.2f / 50", internal50)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}