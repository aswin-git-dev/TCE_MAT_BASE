package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matbase.R
import com.example.matbase.databinding.FragmentDepartmentsBinding

class DepartmentsFragment : Fragment() {
    private var _binding: FragmentDepartmentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDepartmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val clickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_nav_departments_to_nav_semesters)
        }

        binding.deptIt.setOnClickListener(clickListener)
        binding.deptCse.setOnClickListener(clickListener)
        binding.deptCsbs.setOnClickListener(clickListener)
        binding.deptMech.setOnClickListener(clickListener)
        binding.deptMect.setOnClickListener(clickListener)
        binding.deptEee.setOnClickListener(clickListener)
        binding.deptEce.setOnClickListener(clickListener)
        binding.deptCivil.setOnClickListener(clickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}