package com.example.matbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.databinding.FragmentDepartmentBinding

class DepartmentFragment : Fragment() {

    private var _binding: FragmentDepartmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDepartmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val departments = listOf("IT", "CSE", "CSBS", "MECH", "MECT", "EEE", "ECE", "CIVIL")
        val adapter = DepartmentAdapter(departments) {
            val action = DepartmentFragmentDirections.actionDepartmentFragmentToSemesterFragment(it)
            findNavController().navigate(action)
        }
        binding.departmentRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
