package com.tuwaiq.bind.app.feeds

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuwaiq.bind.R
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.databinding.FeedItemBinding
import com.tuwaiq.bind.databinding.FeedsFragmentBinding
import com.tuwaiq.bind.domain.models.PostData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private  val viewModel: FeedsViewModel by viewModels()
    private lateinit var binding: FeedsFragmentBinding
    var userLat:Double =0.0
    var userLon:Double =0.0
    private lateinit var userLocation: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getLocation().observe(
            this,{
                userLocation = it
                userLat = userLocation.latitude
                userLon = userLocation.longitude

                viewModel.getPost(userLat,userLon).observe(
                    this, Observer { posts ->
                        binding.postRv.adapter = PostsAdapter(posts)
                    }
                )
            }
        )

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedsFragmentBinding.inflate(layoutInflater)
        binding.postRv.layoutManager = LinearLayoutManager(context)
        binding.addNewPostBtn.setOnClickListener {
            findNavController().navigate(R.id.action_feedsFragment_to_addPostFragment2)
        }

        return binding.root
    }

    private inner class PostsHolder(val binding:FeedItemBinding):
        RecyclerView.ViewHolder(binding.root){
            fun bind(post: PostData){
                binding.postTimeTv.text = post.postTime
                binding.postTv.text = post.postText
            }
        }

    private inner class PostsAdapter(val posts:List<PostData>):
        RecyclerView.Adapter<PostsHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsHolder {
            val binding = FeedItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return PostsHolder(binding)
        }

        override fun onBindViewHolder(holder: PostsHolder, position: Int) {
            val post = posts[position]
            holder.bind(post)
        }

        override fun getItemCount(): Int = posts.size

    }

}