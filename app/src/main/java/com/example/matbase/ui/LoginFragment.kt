package com.example.matbase.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matbase.R
import com.example.matbase.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var signInLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("874173283981-h4ipqjb75qednbhstg8rg0plgsm4lr5k.apps.googleusercontent.com")
            .requestEmail()
            .requestProfile()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        signInLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            toggleLoading(false)
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                val errorMessage = when (e.statusCode) {
                    7 -> "Network Error. Please check your internet."
                    10 -> "Developer Error. Ensure SHA-1 is added to Firebase Console."
                    12500 -> "Sign-in failed. Please update Google Play Services."
                    else -> "Error code: ${e.statusCode}. Message: ${e.message}"
                }
                Toast.makeText(context, "Login Failed: $errorMessage", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentUser = auth.currentUser
        if (currentUser != null && isValidEmail(currentUser.email)) {
            navigateToHome()
        }

        binding.btnGoogleLogin.setOnClickListener {
            signIn()
        }
    }

    private fun isValidEmail(email: String?): Boolean {
        if (email == null) return false
        val lowerEmail = email.lowercase()
        return lowerEmail.endsWith("@tce.edu") || lowerEmail.endsWith("@student.tce.edu")
    }

    private fun signIn() {
        toggleLoading(true)
        googleSignInClient.signOut().addOnCompleteListener {
            val signInIntent = googleSignInClient.signInIntent
            signInLauncher.launch(signInIntent)
        }
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.btnGoogleLogin.isEnabled = !isLoading
        binding.btnGoogleLogin.alpha = if (isLoading) 0.5f else 1.0f
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val email = acct.email
        if (!isValidEmail(email)) {
            googleSignInClient.signOut()
            Toast.makeText(context, "Unauthorized domain. Use @tce.edu or @student.tce.edu.", Toast.LENGTH_LONG).show()
            return
        }

        val sharedPref = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("user_name", acct.displayName)
            putString("user_email", acct.email)
            putString("user_photo", acct.photoUrl?.toString())
            apply()
        }

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                toggleLoading(false)
                if (task.isSuccessful) {
                    navigateToHome()
                } else {
                    Toast.makeText(context, "Firebase Auth Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_nav_login_to_nav_departments)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
