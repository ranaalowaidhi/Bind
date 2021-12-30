package com.tuwaiq.bind.data.repos

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FeedsRepoImpl"
class FeedsRepoImpl @Inject constructor(
    private val context: Context) :FeedsRepo{

    private val postCollectionRef:CollectionReference = Firebase.firestore.collection("post")
    private val storageRef = Firebase.storage.reference

    override suspend fun addPost(postData: PostData) {
        postCollectionRef.add(postData).await()
    }

    @SuppressLint("MissingPermission")
    override suspend fun getPost(userLat:Double,userLon:Double) : LiveData<List<PostData>> {
        return liveData{
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

            val postList = postDataList.map {
                it.toPostData()
            }
            emit(postList)
        }
    }

    override  fun uploadImgToStorage(filename: String, uri: Uri):LiveData<String>{
       return liveData(Dispatchers.IO) {
           val ref = storageRef.child("images/").child(filename)
           val uploadTask = ref.putFile(uri)

           val urlTask = uploadTask.continueWithTask { task ->
               if (!task.isSuccessful) {
                   task.exception?.let {
                       throw it
                   }
               }
               ref.downloadUrl
           }.await()
           emit(urlTask.toString())
       }




    }

//    override suspend fun getImgFromStorage(filename: String):String{
//            val url = storageRef.child("images/").child(filename).downloadUrl.addOnSuccessListener {
//
//            }
//            return url.toString()
//        }

}