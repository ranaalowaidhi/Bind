package com.tuwaiq.bind.data.remote

import com.tuwaiq.bind.domain.models.PostComment

data class PostCommentDto(
    val commentText:String = "",
    val commentTime:String = "",
    val commentOwner:String = "",
    val postId:String = ""
){
    fun toPostComments():PostComment{
        return PostComment(
            commentText = commentText,
            commentTime = commentTime,
            commentOwner = commentOwner,
            postId = postId
        )
    }
}
