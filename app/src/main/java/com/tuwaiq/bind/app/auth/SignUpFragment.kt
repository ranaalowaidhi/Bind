package com.tuwaiq.bind.app.auth

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.tuwaiq.bind.R
import com.tuwaiq.bind.databinding.SignUpFragmentBinding
import com.tuwaiq.bind.databinding.SplashFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "SignUpFragment"

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private  val viewModel:AuthViewModel by viewModels()

    private lateinit var binding: SignUpFragmentBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignUpFragmentBinding.inflate(layoutInflater)

        binding.signupBtn.setOnClickListener {
            val username = binding.signupName.text.toString()
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()


            viewModel.signup(username,email,password)
            Log.d(TAG,"$username $email $password")
            findNavController().navigate(R.id.action_signUpFragment_to_feedsFragment)
        }

        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        return binding.root
    }

}