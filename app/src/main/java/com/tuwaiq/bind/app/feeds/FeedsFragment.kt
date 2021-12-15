package com.tuwaiq.bind.app.feeds

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuwaiq.bind.R
import com.tuwaiq.bind.app.auth.AuthViewModel
import com.tuwaiq.bind.databinding.FeedsFragmentBinding
import com.tuwaiq.bind.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private  val viewModel: FeedsViewModel by viewModels()
    private lateinit var binding: FeedsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedsFragmentBinding.inflate(layoutInflater)

        binding.addNewPostBtn.setOnClickListener {
            findNavController().navigate(R.id.action_feedsFragment_to_addPostFragment2)
        }

        return binding.root
    }


}