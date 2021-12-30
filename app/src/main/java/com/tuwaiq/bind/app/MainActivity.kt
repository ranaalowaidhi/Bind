package com.tuwaiq.bind.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tuwaiq.bind.R
import com.tuwaiq.bind.app.feeds.AddPostFragment
import dagger.hilt.android.AndroidEntryPoint
import android.widget.Toast

import com.sandrios.sandriosCamera.internal.SandriosCamera

import android.app.Activity
import android.net.Uri
import com.sandrios.sandriosCamera.internal.ui.model.Media
import android.text.format.DateUtils

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    interface CallBack{
        fun onResultRecived(media: Media)
    }

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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == SandriosCamera.RESULT_CODE && data != null) {
            if (data.getSerializableExtra(SandriosCamera.MEDIA) is Media) {
                val media: Media = (data.getSerializableExtra(SandriosCamera.MEDIA) as Media)
                Log.e("File", "" + media?.path)
                Log.e("Type", "" + media?.type)
                Toast.makeText(applicationContext, "Media captured.", Toast.LENGTH_SHORT).show()
                (AddPostFragment() as CallBack ).onResultRecived(media)
            }
        }

    }



}