package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import androidx.lifecycle.LiveData
import com.tuwaiq.bind.data.remote.PostDataDto
import com.tuwaiq.bind.domain.models.PostData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val feedsRepo:FeedsRepo
) {

    suspend operator fun invoke(userLat:Double,userLon:Double):LiveData<List<PostData>> =
        feedsRepo.getPost(userLat,userLon)

}