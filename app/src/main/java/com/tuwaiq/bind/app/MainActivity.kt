package com.tuwaiq.bind.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tuwaiq.bind.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavBar:BottomNavigationView
    private lateinit var bottomAppBar:BottomAppBar
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavBar = findViewById(R.id.bottomNavigationView)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        fab = findViewById(R.id.fab)
        bottomNavBar.background = null
        bottomNavBar.menu.getItem(2).isEnabled = false

        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavBar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if(destination.id == R.id.splashFragment || destination.id == R.id.signUpFragment
                || destination.id == R.id.loginFragment ) {
                bottomNavBar.visibility = View.GONE
                bottomAppBar.visibility = View.GONE
                fab.visibility = View.GONE
            } else {
                bottomNavBar.visibility = View.VISIBLE
                bottomAppBar.visibility = View.VISIBLE
                fab.visibility = View.VISIBLE
            }
        }

        fab.setOnClickListener {
            navController.navigate(R.id.addPostFragment)
        }

    }
}