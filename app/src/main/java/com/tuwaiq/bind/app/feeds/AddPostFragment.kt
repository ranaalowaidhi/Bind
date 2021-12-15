package com.tuwaiq.bind.app.feeds

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.tuwaiq.bind.R
import com.tuwaiq.bind.data.remote.PostData
import com.tuwaiq.bind.databinding.AddPostFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostFragment : Fragment() {

    private  val viewModel: AddPostViewModel by viewModels()
    private lateinit var binding: AddPostFragmentBinding
    private lateinit var userLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getLocation().observe(
            this,{
                userLocation = it
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPostFragmentBinding.inflate(layoutInflater)
        binding.addPostBtn.setOnClickListener {
            val post = PostData("12345","hello,world","empty","1 hours ago",userLocation)
            viewModel.addPost(post)
            findNavController().navigate(R.id.action_addPostFragment_to_feedsFragment)
        }

        return binding.root
    }


}