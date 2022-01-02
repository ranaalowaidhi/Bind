package com.tuwaiq.bind.domain.use_cases.feeds_use_cases

import com.tuwaiq.bind.domain.models.PostComment
import com.tuwaiq.bind.domain.repos.FeedsRepo
import javax.inject.Inject

class AddCommentUseCase @Inject constructor(
    private val feedsRepo:FeedsRepo
) {

    suspend operator fun invoke(postComment: PostComment) =
        feedsRepo.addComment(postComment)

}