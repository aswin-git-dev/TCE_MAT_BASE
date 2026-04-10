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
        
        val defaultClickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_nav_departments_to_nav_semesters)
        }

        // IT Department - Opens Google Drive in app with the new link
        binding.deptIt.setOnClickListener {
            val bundle = Bundle().apply {
                putString("url", "https://drive.google.com/drive/folders/1RJJ-b3hmDToCZw_1ZRqWLZPIUWbsUfx5")
            }
            findNavController().navigate(R.id.action_nav_departments_to_driveViewerFragment, bundle)
        }

        // Other departments continue to use the semester-wise navigation
        binding.deptCse.setOnClickListener(defaultClickListener)
        binding.deptCsbs.setOnClickListener(defaultClickListener)
        binding.deptMech.setOnClickListener(defaultClickListener)
        binding.deptMect.setOnClickListener(defaultClickListener)
        binding.deptEee.setOnClickListener(defaultClickListener)
        binding.deptEce.setOnClickListener(defaultClickListener)
        binding.deptCivil.setOnClickListener(defaultClickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}