package com.tuwaiq.bind.domain.repos

import com.tuwaiq.bind.domain.models.PostData

interface FeedsRepo {

    suspend fun addPost(postData: PostData)

    suspend fun getPost(userLat:Double,userLon:Double):List<PostData>


}