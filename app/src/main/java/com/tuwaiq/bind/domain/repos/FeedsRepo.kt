package com.tuwaiq.bind.domain.repos

import android.graphics.Bitmap
import android.net.Uri
import com.tuwaiq.bind.domain.models.PostData

interface FeedsRepo {

    suspend fun addPost(postData: PostData)

    suspend fun getPost(userLat:Double,userLon:Double):List<PostData>

    suspend fun uploadImgToStorage(filename:String,uri: Uri)

    suspend fun downloadImgFromStorage(filename: String):Bitmap

}