package com.tuwaiq.bind.domain.repos

import com.tuwaiq.bind.data.remote.PostData

interface FeedsRepo {

    suspend fun addPost(postData: PostData)

}