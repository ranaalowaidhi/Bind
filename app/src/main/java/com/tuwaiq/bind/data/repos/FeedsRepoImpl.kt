package com.tuwaiq.bind.data.repos

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.Transformations.map
import com.google.common.io.Files.map
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FeedsRepoImpl"
class FeedsRepoImpl @Inject constructor(
    private val context: Context) :FeedsRepo{

    private val postCollectionRef:CollectionReference = Firebase.firestore.collection("post")

    override suspend fun addPost(postData: PostData) {
        postCollectionRef.add(postData).await()
    }

    @SuppressLint("MissingPermission")
    override suspend fun getPost(userLat:Double,userLon:Double) : List<PostData> {
        val postDataList: MutableList<PostDataDto> = mutableListOf()
        val snapshot = postCollectionRef.get().await()
        for (document in snapshot) {
            val postLat: Double = document["postLat"].toString().toDouble()
            val postLon: Double = document["postLan"].toString().toDouble()
            val results = FloatArray(3)
            Location.distanceBetween(
                userLat, userLon,
                postLat, postLon, results
            )
            val distance = results[0]
            Log.d(TAG,"$distance")
            if (distance <= 5000.0f) {
                val postData = document.toObject(PostDataDto::class.java)
                postDataList += postData
                Log.d(TAG, "getPost: $postData")
            }else{
                Log.d(TAG, "getPost: Nothing to show")
            }
        }
        return postDataList.map {
            it.toPostData()
        }
    }
}