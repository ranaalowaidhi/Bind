package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import com.tuwaiq.bind.data.remote.PostData
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class AddPostUseCase @Inject constructor(
    private val feedsRepo: FeedsRepo
) {

    suspend operator fun invoke(postData: PostData)=
        feedsRepo.addPost(postData)

}