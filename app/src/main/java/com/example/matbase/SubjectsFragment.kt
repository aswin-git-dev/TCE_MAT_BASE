package com.example.matbase

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.matbase.databinding.FragmentSubjectsBinding

class SubjectsFragment : Fragment() {

    private var _binding: FragmentSubjectsBinding? = null
    private val binding get() = _binding!!

    private val args: SubjectsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubjectsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.semesterTitle.text = args.semester

        val subjects = (1..8).map { "SUB$it" }
        val adapter = SubjectsAdapter(subjects) {
            // Handle subject click
        }
        binding.subjectsRecyclerView.adapter = SubjectsAdapter(subjects) { subject ->
            val action =
                SubjectsFragmentDirections
                    .actionNavSubjectsToNavSubjectOptions(subject)

            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
