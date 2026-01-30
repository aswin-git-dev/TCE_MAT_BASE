package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.matbase.databinding.FragmentSubjectOptionsBinding

class SubjectOptionsFragment : Fragment() {

    private var _binding: FragmentSubjectOptionsBinding? = null
    private val binding get() = _binding!!

    private val args: SubjectOptionsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSubjectOptionsBinding.inflate(inflater, container, false)

        // Set subject title
        binding.subjectTitle.text = args.subjectName

        // Access the root of each included binding for clicks
        binding.cardStudyMaterials.root.setOnClickListener { }
        binding.cardStudyMaterials.optionTitle.text = "Study Materials"

        binding.cardPyqs.root.setOnClickListener { }
        binding.cardPyqs.optionTitle.text = "PYQs"

        binding.cardLabManuals.root.setOnClickListener { }
        binding.cardLabManuals.optionTitle.text = "Lab Manuals"

        binding.cardSyllabus.root.setOnClickListener { }
        binding.cardSyllabus.optionTitle.text = "Syllabus"

        binding.cardGradeCalculator.root.setOnClickListener { }
        binding.cardGradeCalculator.optionTitle.text = "Grade Calculator"


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
