package com.tuwaiq.bind.domain.repos

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import com.tuwaiq.bind.domain.models.PostData

interface FeedsRepo {

    suspend fun addPost(postData: PostData)

    suspend fun getPost(userLat:Double,userLon:Double):LiveData<List<PostData>>

     fun uploadImgToStorage(filename:String,uri: Uri):LiveData<String>

//    suspend fun getImgFromStorage(filename: String):LiveData<String>

}