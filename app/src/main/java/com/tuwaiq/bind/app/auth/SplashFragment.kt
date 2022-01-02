package com.tuwaiq.bind.app.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.R
import com.tuwaiq.bind.databinding.SplashFragmentBinding

class SplashFragment : Fragment() {

    private lateinit var binding:SplashFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if ( Firebase.auth.currentUser !=  null) {
            findNavController().navigate(R.id.feedsFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SplashFragmentBinding.inflate(layoutInflater)
        binding.goLoginBtn.setOnClickListener {
           view?.let { view -> Navigation.findNavController(view)
               .navigate(R.id.action_splashFragment_to_loginFragment) }
        }

        binding.goSignupBtn.setOnClickListener {
            view?.let { view -> Navigation.findNavController(view)
                .navigate(R.id.action_splashFragment_to_signUpFragment) }
        }
        return binding.root
    }





}