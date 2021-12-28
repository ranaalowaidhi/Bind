package com.tuwaiq.bind.app.feeds

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.R
import com.tuwaiq.bind.databinding.AddPostFragmentBinding
import com.tuwaiq.bind.domain.models.PostData
import dagger.hilt.android.AndroidEntryPoint
import io.ak1.pix.helpers.PixEventCallback
import io.ak1.pix.helpers.addPixToActivity
import io.ak1.pix.helpers.pixFragment
import io.ak1.pix.models.Flash
import io.ak1.pix.models.Mode
import io.ak1.pix.models.Options
import io.ak1.pix.models.Ratio
import io.ak1.pix.utility.WIDTH
import lv.chi.photopicker.ChiliPhotoPicker
import lv.chi.photopicker.PhotoPickerFragment

private const val TAG = "AddPostFragment"
@AndroidEntryPoint
class AddPostFragment : Fragment(){

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


        binding.addImgBtn.setOnClickListener{
            startActivity(Intent(requireContext(), FragmentSample::class.java))
        }



        binding.addPostBtn.setOnClickListener {
            val postLat = userLocation.latitude.toString()
            val postLan = userLocation.longitude.toString()
            val postOwner = Firebase.auth.currentUser?.uid.toString()
            val post = PostData(postOwner,"hello,world","empty",
                "1 hours ago",postLat,postLan)
            viewModel.addPost(post)
            findNavController().navigate(R.id.action_addPostFragment_to_feedsFragment)
        }

        return binding.root
    }

}




