package com.example.matbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.matbase.databinding.FragmentSemesterBinding

class SemesterFragment : Fragment() {

    private var _binding: FragmentSemesterBinding? = null
    private val binding get() = _binding!!

    private val args: SemesterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSemesterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.departmentTitle.text = args.department

        val semesters = listOf("1st SEM", "2nd SEM", "3rd SEM", "4th SEM", "5th SEM", "6th SEM", "7th SEM", "8th SEM")
        val adapter = SemesterAdapter(semesters) {
            findNavController().navigate(R.id.action_semesterFragment_to_mainActivity)
        }
        binding.semesterRecyclerView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
