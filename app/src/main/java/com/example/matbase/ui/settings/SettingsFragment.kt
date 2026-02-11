package com.example.matbase.ui.settings

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.matbase.R
import com.example.matbase.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val CHANNEL_ID = "settings_notification"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        settingsViewModel.text.observe(viewLifecycleOwner) {
            binding.textSettings.text = it
        }

        binding.btnSaveSettings.setOnClickListener {
            saveAndNotify()
        }

        createNotificationChannel()

        return root
    }

    private fun saveAndNotify() {
        val day = binding.datePicker.dayOfMonth
        val month = binding.datePicker.month + 1
        val year = binding.datePicker.year
        val hour = binding.timePicker.hour
        val minute = binding.timePicker.minute

        // Progress Dialog usage
        val progressDialog = ProgressDialog(requireContext())
        progressDialog.setMessage("Saving settings...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        // Simulate a delay
        Handler(Looper.getMainLooper()).postDelayed({
            progressDialog.dismiss()
            
            val dateTime = "$day/$month/$year $hour:$minute"
            Toast.makeText(requireContext(), "Settings saved for $dateTime", Toast.LENGTH_SHORT).show()
            
            sendNotification("Settings Updated", "Notification set for $dateTime")
        }, 2000)
    }

    private fun sendNotification(title: String, message: String) {
        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(1, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Settings Channel"
            val descriptionText = "Channel for settings notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
