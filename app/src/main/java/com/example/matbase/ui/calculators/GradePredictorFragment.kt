package com.example.matbase.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matbase.databinding.FragmentGradePredictorBinding

class GradePredictorFragment : Fragment() {

    private var _binding: FragmentGradePredictorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGradePredictorBinding.inflate(inflater, container, false)

        binding.btnPredictGrade.setOnClickListener {
            val marksStr = binding.etMarks.text.toString()
            if (marksStr.isNotEmpty()) {
                val marks = marksStr.toDouble()
                val (grade, points) = calculateGrade(marks)
                
                binding.tvPredictedGrade.text = grade
                binding.tvGradePoints.text = "Grade Points: $points"
                binding.cardGradeResult.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Please enter marks", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun calculateGrade(marks: Double): Pair<String, Int> {
        return when {
            marks >= 91 -> "O" to 10
            marks >= 81 -> "A+" to 9
            marks >= 71 -> "A" to 8
            marks >= 61 -> "B+" to 7
            marks >= 55 -> "B" to 6
            marks >= 50 -> "C" to 5
            else -> "U" to 0
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}