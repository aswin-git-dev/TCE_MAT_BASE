package com.example.matbase.ui.calculators

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.matbase.databinding.FragmentSgpaPredictorBinding
import com.example.matbase.databinding.ItemSgpaSubjectBinding

class SGPAPredictorFragment : Fragment() {

    private var _binding: FragmentSgpaPredictorBinding? = null
    private val binding get() = _binding!!

    private val subjects = mutableListOf<SGPASubject>()
    private lateinit var adapter: SGPAAdapter

    data class SGPASubject(var name: String = "", var credits: Int = 0, var gradePoint: Int = 0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSgpaPredictorBinding.inflate(inflater, container, false)

        adapter = SGPAAdapter(subjects)
        binding.rvSgpaSubjects.adapter = adapter
        binding.rvSgpaSubjects.layoutManager = LinearLayoutManager(requireContext())

        // Add initial subjects
        if (subjects.isEmpty()) {
            repeat(3) { subjects.add(SGPASubject()) }
            adapter.notifyDataSetChanged()
        }

        binding.btnAddSubject.setOnClickListener {
            subjects.add(SGPASubject())
            adapter.notifyItemInserted(subjects.size - 1)
        }

        binding.btnCalculateSgpa.setOnClickListener {
            calculateSGPA()
        }

        return binding.root
    }

    private fun calculateSGPA() {
        var totalGradePoints = 0.0
        var totalCredits = 0.0

        for (subject in subjects) {
            if (subject.credits > 0) {
                totalGradePoints += (subject.gradePoint * subject.credits)
                totalCredits += subject.credits
            }
        }

        if (totalCredits > 0) {
            val sgpa = totalGradePoints / totalCredits
            binding.tvSgpaResult.text = String.format("Predicted SGPA: %.2f", sgpa)
        } else {
            Toast.makeText(requireContext(), "Please enter credits for subjects", Toast.LENGTH_SHORT).show()
        }
    }

    inner class SGPAAdapter(private val items: MutableList<SGPASubject>) :
        RecyclerView.Adapter<SGPAAdapter.ViewHolder>() {

        private val grades = arrayOf("O", "A+", "A", "B+", "B", "C", "U")
        private val gradePoints = mapOf("O" to 10, "A+" to 9, "A" to 8, "B+" to 7, "B" to 6, "C" to 5, "U" to 0)

        inner class ViewHolder(val binding: ItemSgpaSubjectBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemSgpaSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val subject = items[position]
            
            holder.binding.etSubjectName.setText(subject.name)
            if (subject.credits > 0) holder.binding.etCredits.setText(subject.credits.toString()) else holder.binding.etCredits.setText("")
            
            val gradeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, grades)
            holder.binding.actvGrade.setAdapter(gradeAdapter)
            
            // Set current grade selection
            val currentGrade = gradePoints.entries.find { it.value == subject.gradePoint }?.key ?: "U"
            holder.binding.actvGrade.setText(currentGrade, false)

            holder.binding.etSubjectName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) subject.name = holder.binding.etSubjectName.text.toString()
            }

            holder.binding.etCredits.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    subject.credits = holder.binding.etCredits.text.toString().toIntOrNull() ?: 0
                }
            }

            holder.binding.actvGrade.setOnItemClickListener { _, _, i, _ ->
                val selectedGrade = grades[i]
                subject.gradePoint = gradePoints[selectedGrade] ?: 0
            }

            holder.binding.btnRemoveSubject.setOnClickListener {
                if (items.size > 1) {
                    val currentPos = holder.adapterPosition
                    items.removeAt(currentPos)
                    notifyItemRemoved(currentPos)
                }
            }
        }

        override fun getItemCount() = items.size
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}