package com.tuwaiq.bind.app.feeds

import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.R
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.databinding.FeedItemBinding
import com.tuwaiq.bind.databinding.FeedsFragmentBinding
import com.tuwaiq.bind.domain.models.PostData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
private const val TAG = "FeedsFragment"
@AndroidEntryPoint
class FeedsFragment : Fragment() {

    private  val viewModel: FeedsViewModel by viewModels()
    private lateinit var binding: FeedsFragmentBinding
    var userLat:Double =0.0
    var userLon:Double =0.0
    private lateinit var userLocation: Location
    private lateinit var bitmap: Bitmap
    var gotBitmap = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getLocation().observe(
                this@FeedsFragment,{
                    userLocation = it
                    userLat = userLocation.latitude
                    userLon = userLocation.longitude

                    lifecycleScope.launch {
                        viewModel.getPost(userLat,userLon).observe(
                            this@FeedsFragment, Observer { posts ->
                                binding.postRv.adapter = PostsAdapter(posts)
                            }
                        )
                    }
                }
            )
        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FeedsFragmentBinding.inflate(layoutInflater)
        binding.postRv.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    private inner class PostsHolder(val binding:FeedItemBinding):
        RecyclerView.ViewHolder(binding.root){

            fun bind(post: PostData){
                val dateStr = post.postTime
                val dateTime = Date.parse(dateStr)
                val postDate = getTimeAgo(dateTime)
                binding.postTimeTv.text = postDate
                binding.postTv.text = post.postText
                binding.usernameTv.text = post.userName
                Glide.with(this@FeedsFragment)
                    .load(post.postPhoto)
                    .into(binding.postImg)
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

    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    fun getTimeAgo(date: Long): String {
        var time = date
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = currentDate().time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "moments ago"
            diff < 2 * MINUTE_MILLIS -> "minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} minutes ago"
            diff < 2 * HOUR_MILLIS -> "hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} hours ago"
            diff < 48 * HOUR_MILLIS -> "yesterday"
            else -> "${diff / DAY_MILLIS} days ago"
        }
    }

}