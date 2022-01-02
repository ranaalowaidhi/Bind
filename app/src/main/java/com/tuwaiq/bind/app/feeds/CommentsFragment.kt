package com.tuwaiq.bind.app.feeds

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.R
import com.tuwaiq.bind.commen.utils.hideKeyboard
import com.tuwaiq.bind.databinding.CommentItemBinding
import com.tuwaiq.bind.databinding.FeedItemBinding
import com.tuwaiq.bind.databinding.FeedsFragmentBinding
import com.tuwaiq.bind.databinding.FragmentCommentsBinding
import com.tuwaiq.bind.domain.models.PostComment
import com.tuwaiq.bind.domain.models.PostData
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
private const val SECOND_MILLIS = 1000
private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
private const val DAY_MILLIS = 24 * HOUR_MILLIS
private const val TAG = "CommentsFragment"
@AndroidEntryPoint
class CommentsFragment : Fragment() {

    private  val viewModel: FeedsViewModel by viewModels()
    private lateinit var binding: FragmentCommentsBinding

    val args:CommentsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            viewModel.getComments(args.postId).observe(
                this@CommentsFragment,{
                    binding.commentsRv.adapter = CommentsAdapter(it)
                }
            )
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentsBinding.inflate(layoutInflater)
        binding.commentsRv.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    private inner class CommentsHolder(val binding:CommentItemBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(comment: PostComment){
            val dateStr = comment.commentTime
            val dateTime = Date.parse(dateStr)
            val commentTime = getTimeAgo(dateTime)
            binding.commentTimeTv.text = commentTime
            binding.commentTv.text = comment.commentText

        }
    }


    private inner class CommentsAdapter(val comments:List<PostComment>):
        RecyclerView.Adapter<CommentsFragment.CommentsHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsFragment.CommentsHolder {
            val binding = CommentItemBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return CommentsHolder(binding)
        }

        override fun onBindViewHolder(holder: CommentsFragment.CommentsHolder, position: Int) {
            val comment = comments[position]
            holder.bind(comment)
        }

        override fun getItemCount(): Int = comments.size

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