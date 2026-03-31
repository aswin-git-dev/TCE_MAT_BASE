package com.example.matbase.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.matbase.R
import com.example.matbase.databinding.FragmentAttendanceCalculatorBinding

class AttendanceCalculatorFragment : Fragment() {

    private var _binding: FragmentAttendanceCalculatorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttendanceCalculatorBinding.inflate(inflater, container, false)

        binding.btnCalculateAttendance.setOnClickListener {
            val totalStr = binding.etTotalPeriods.text.toString()
            val skippedStr = binding.etSkippedPeriods.text.toString()

            if (totalStr.isNotEmpty() && skippedStr.isNotEmpty()) {
                val total = totalStr.toDouble()
                val skipped = skippedStr.toDouble()
                
                if (skipped > total) {
                    Toast.makeText(requireContext(), "Skipped cannot be more than total", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val attended = total - skipped
                val percentage = (attended / total) * 100
                val maxSkips = (total * 0.25).toInt()
                val remainingSkips = maxSkips - skipped.toInt()

                binding.tvAttendancePercentage.text = String.format("%.1f%%", percentage)
                
                if (percentage >= 75) {
                    binding.tvAttendancePercentage.setTextColor(resources.getColor(R.color.success_green, null))
                    binding.tvAttendanceStatus.text = "Safe to attend exams"
                    binding.tvAttendanceStatus.setTextColor(resources.getColor(R.color.success_green, null))
                    binding.tvRemainingSkips.text = if (remainingSkips > 0) 
                        "You can skip $remainingSkips more classes" 
                        else "Don't skip any more classes!"
                } else {
                    binding.tvAttendancePercentage.setTextColor(resources.getColor(R.color.error_red, null))
                    binding.tvAttendanceStatus.text = "Lack of Attendance - Repeat Course Needed"
                    binding.tvAttendanceStatus.setTextColor(resources.getColor(R.color.error_red, null))
                    binding.tvRemainingSkips.text = "You must attend all remaining classes"
                }
                
                binding.cardAttendanceResult.visibility = View.VISIBLE
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}