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

import com.sandrios.sandriosCamera.internal.ui.model.Media
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val rotateOpen:Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim) }
    private val rotateClose:Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim) }
    private val fromBottom:Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim) }
    private val toBottom:Animation by lazy { AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim) }

    interface CallBack{
        fun onResultRecived(media: Media)
    }

    private lateinit var bottomNavBar:BottomNavigationView
    private lateinit var bottomAppBar:BottomAppBar
    private lateinit var fab: FloatingActionButton
    private lateinit var textPostBtn: FloatingActionButton
    private lateinit var mediaPostBtn: FloatingActionButton
    private lateinit var voicePostBtn: FloatingActionButton

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavBar = findViewById(R.id.bottomNavigationView)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        fab = findViewById(R.id.fab)
        textPostBtn = findViewById(R.id.textPostBtn)
        mediaPostBtn = findViewById(R.id.mediaPostBtn)
        voicePostBtn = findViewById(R.id.voicePostBtn)

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
            onAddBtnClick()
        }
        textPostBtn.setOnClickListener {
            navController.navigate(R.id.addPostFragment)
            onAddBtnClick()
        }
        mediaPostBtn.setOnClickListener {
            navController.navigate(R.id.addPostFragment)
            onAddBtnClick()
        }
        voicePostBtn.setOnClickListener {
            navController.navigate(R.id.addPostFragment)
            onAddBtnClick()
        }


    }

    private fun onAddBtnClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        setClickable(clicked)
        clicked = !clicked
    }

    private fun setClickable(clicked: Boolean) {
        if(!clicked){
            textPostBtn.isClickable = true
            mediaPostBtn.isClickable = true
            voicePostBtn.isClickable = true
        }else{
            textPostBtn.isClickable = false
            mediaPostBtn.isClickable = false
            voicePostBtn.isClickable = false
        }
    }

    private fun setAnimation(clicked:Boolean) {
        if (!clicked){
            textPostBtn.visibility = View.VISIBLE
            mediaPostBtn.visibility = View.VISIBLE
            voicePostBtn.visibility = View.VISIBLE
        }else{
            textPostBtn.visibility = View.INVISIBLE
            mediaPostBtn.visibility = View.INVISIBLE
            voicePostBtn.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked:Boolean) {
        if (!clicked){
            textPostBtn.startAnimation(fromBottom)
            mediaPostBtn.startAnimation(fromBottom)
            voicePostBtn.startAnimation(fromBottom)
            fab.startAnimation(rotateOpen)
        }else{
            textPostBtn.startAnimation(toBottom)
            mediaPostBtn.startAnimation(toBottom)
            voicePostBtn.startAnimation(toBottom)
            fab.startAnimation(rotateClose)
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