package com.example.matbase.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.matbase.R
import com.example.matbase.databinding.FragmentCalculatorsBinding

class CalculatorsFragment : Fragment() {

    private var _binding: FragmentCalculatorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalculatorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        // Default fragment
        replaceCalculatorFragment(SGPAPredictorFragment())
    }

    private fun setupTabs() {
        binding.tabSgpa.setOnClickListener {
            updateTabUI(0)
            replaceCalculatorFragment(SGPAPredictorFragment())
        }
        binding.tabGrade.setOnClickListener {
            updateTabUI(1)
            replaceCalculatorFragment(GradePredictorFragment())
        }
        binding.tabAttendance.setOnClickListener {
            updateTabUI(2)
            replaceCalculatorFragment(AttendanceCalculatorFragment())
        }
        binding.tabInternal.setOnClickListener {
            updateTabUI(3)
            replaceCalculatorFragment(InternalCalculatorFragment())
        }
    }

    private fun updateTabUI(index: Int) {
        val activeColor = ContextCompat.getColor(requireContext(), R.color.purple_700)
        val inactiveColor = ContextCompat.getColor(requireContext(), R.color.black)

        binding.indicatorSgpa.visibility = if (index == 0) View.VISIBLE else View.INVISIBLE
        binding.tvSgpa.setTextColor(if (index == 0) activeColor else inactiveColor)
        binding.ivSgpa.setColorFilter(if (index == 0) activeColor else inactiveColor)

        binding.indicatorGrade.visibility = if (index == 1) View.VISIBLE else View.INVISIBLE
        binding.tvGrade.setTextColor(if (index == 1) activeColor else inactiveColor)
        binding.ivGrade.setColorFilter(if (index == 1) activeColor else inactiveColor)

        binding.indicatorAttendance.visibility = if (index == 2) View.VISIBLE else View.INVISIBLE
        binding.tvAttendance.setTextColor(if (index == 2) activeColor else inactiveColor)
        binding.ivAttendance.setColorFilter(if (index == 2) activeColor else inactiveColor)

        binding.indicatorInternal.visibility = if (index == 3) View.VISIBLE else View.INVISIBLE
        binding.tvInternal.setTextColor(if (index == 3) activeColor else inactiveColor)
        binding.ivInternal.setColorFilter(if (index == 3) activeColor else inactiveColor)
    }

    private fun replaceCalculatorFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.calculator_container, fragment)
            .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}