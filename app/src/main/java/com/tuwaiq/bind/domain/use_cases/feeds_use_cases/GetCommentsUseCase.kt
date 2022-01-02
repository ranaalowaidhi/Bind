package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import androidx.lifecycle.LiveData
import com.tuwaiq.bind.domain.models.PostComment
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val feedsRepo: FeedsRepo
) {

    suspend operator fun invoke(postId:String): LiveData<List<PostComment>> =
        feedsRepo.getComments(postId)

}