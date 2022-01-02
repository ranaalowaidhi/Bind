package com.tuwaiq.bind.app.feeds


import android.annotation.SuppressLint
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sandrios.sandriosCamera.internal.SandriosCamera
import com.sandrios.sandriosCamera.internal.configuration.CameraConfiguration
import com.sandrios.sandriosCamera.internal.ui.model.Media
import com.tuwaiq.bind.R
import com.tuwaiq.bind.app.MainActivity
import com.tuwaiq.bind.databinding.AddPostFragmentBinding
import com.tuwaiq.bind.domain.models.PostData
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

lateinit var uri:Uri
var cameraClicked = false
private const val TAG = "AddPostFragment"
@AndroidEntryPoint
class AddPostFragment : Fragment() , MainActivity.CallBack{

    private  val viewModel: AddPostViewModel by viewModels()
    private lateinit var binding: AddPostFragmentBinding
    private lateinit var userLocation: Location
    lateinit var cMedia:Media

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.getLocation().observe(
                this@AddPostFragment,{
                    userLocation = it
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddPostFragmentBinding.inflate(layoutInflater)
        Log.e(TAG,"binding started")
        binding.addPostBtn.setOnClickListener {
            val date = Date().toString()
            val postLat = userLocation.latitude.toString()
            val postLan = userLocation.longitude.toString()
            val postOwner = Firebase.auth.currentUser?.uid.toString()
            val postText = binding.postTv.text.toString()
            val username:String = Firebase.auth.currentUser?.displayName.toString()
            val photoId:UUID = UUID.randomUUID()
            val postId = "$username-${photoId}"
            var postPhoto = ""
            viewModel.uploadImgToStorage(postId, uri).observe(
                    viewLifecycleOwner,{
                        postPhoto = it
                        val post = PostData(postOwner,postText, postPhoto,date,postLat,postLan,username,postId)
                        viewModel.addPost(post)
                        findNavController().navigate(R.id.action_addPostFragment_to_feedsFragment)
                    }
                )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addImgBtn.setOnClickListener{
            showCamera()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG,"frag on start && $cameraClicked")
        if(cameraClicked){
            binding.imageUri.setImageURI(uri)
        }

    }

    private fun showCamera(){
        SandriosCamera
            .with()
            .setShowPicker(true)
            .setVideoFileSize(20)
            .setMediaAction(CameraConfiguration.MEDIA_ACTION_BOTH)
            .enableImageCropping(true)
            .launchCamera(activity)
    }

    override fun onResultRecived(media: Media) {
        cMedia = media
        val path = media.path
        cameraClicked = true
        uri = Uri.fromFile(File(path))
        Log.e(TAG,"img uri = $uri && $cameraClicked")
    }
}




