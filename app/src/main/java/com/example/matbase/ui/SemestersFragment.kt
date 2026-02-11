package com.example.matbase.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matbase.R
import com.example.matbase.databinding.FragmentSemestersBinding

class SemestersFragment : Fragment() {
    private var _binding: FragmentSemestersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSemestersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val clickListener = View.OnClickListener {
            findNavController().navigate(R.id.action_nav_semesters_to_nav_subjects)
        }

        binding.sem1.setOnClickListener(clickListener)
        binding.sem2.setOnClickListener(clickListener)
        binding.sem3.setOnClickListener(clickListener)
        binding.sem4.setOnClickListener(clickListener)
        binding.sem5.setOnClickListener(clickListener)
        binding.sem6.setOnClickListener(clickListener)
        binding.sem7.setOnClickListener(clickListener)
        binding.sem8.setOnClickListener(clickListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}