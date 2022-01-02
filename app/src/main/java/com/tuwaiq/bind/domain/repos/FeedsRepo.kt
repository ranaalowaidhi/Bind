package com.tuwaiq.bind.domain.repos

import android.net.Uri
import androidx.lifecycle.LiveData
import com.tuwaiq.bind.domain.models.PostComment
import com.tuwaiq.bind.domain.models.PostData

interface FeedsRepo {

    suspend fun addPost(postData: PostData)

    suspend fun getPost(userLat:Double,userLon:Double):LiveData<List<PostData>>

    fun uploadImgToStorage(filename:String,uri: Uri):LiveData<String>

    suspend fun addComment(postComment: PostComment)

    suspend fun getComments(postId:String): LiveData<List<PostComment>>

}