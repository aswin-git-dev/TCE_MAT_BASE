package com.example.matbase.ui.profile

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.example.matbase.R
import com.example.matbase.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadUserData()

        binding.btnAboutUs.setOnClickListener {
            findNavController().navigate(R.id.nav_about_us)
        }

        binding.btnSite.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://materialbase.in"))
            startActivity(browserIntent)
        }

        binding.btnLogout.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun loadUserData() {
        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val name = sharedPref.getString("user_name", "Student Name")
        val email = sharedPref.getString("user_email", "student@tce.edu")
        val photoUrl = sharedPref.getString("user_photo", null)
        
        binding.tvProfileName.text = name
        binding.tvProfileEmail.text = email
        
        if (photoUrl != null) {
            binding.ivProfileImage.load(photoUrl) {
                crossfade(true)
                placeholder(R.drawable.avatar_1)
                error(R.drawable.avatar_1)
                transformations(CircleCropTransformation())
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                FirebaseAuth.getInstance().signOut()
                
                // Clear Shared Preferences
                val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                sharedPref.edit().clear().apply()
                
                // Navigate back to Login
                findNavController().navigate(R.id.nav_login)
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}