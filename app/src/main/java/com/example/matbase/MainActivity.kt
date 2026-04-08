package com.example.matbase

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.matbase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.appBarMain.toolbar)

        // Navigation for Drawer and Bottom Navigation View
        // Added nav_login to top-level destinations so the back button doesn't appear there
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_login, R.id.nav_materials, R.id.nav_calculators, R.id.nav_profile, R.id.nav_about_us
            ),
            binding.drawerLayout
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        
        // Setup Navigation components for different layout configurations
        // 1. Drawer Navigation (if present in activity_main.xml)
        binding.navView?.setupWithNavController(navController)
        
        // 2. Bottom Navigation (if present in content_main.xml)
        binding.appBarMain.contentMain.bottomNavView?.setupWithNavController(navController)
        
        // 3. Side Navigation (for large screens, if present in content_main.xml)
        binding.appBarMain.contentMain.navView?.setupWithNavController(navController)

        // Handle back button
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentDest = navController.currentDestination?.id
                if (currentDest == R.id.nav_materials || currentDest == R.id.nav_login) {
                    showExitDialog()
                } else {
                    if (!navController.navigateUp()) {
                        showExitDialog()
                    }
                }
            }
        })
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit Application")
            .setMessage("Are you sure you want to exit?")
            .setPositiveButton("Yes") { _, _ ->
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.overflow, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Use onNavDestinationSelected to handle menu item clicks automatically if IDs match
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
