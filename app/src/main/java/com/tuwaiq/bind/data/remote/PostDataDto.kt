package com.tuwaiq.bind.data.remote

import android.net.Uri
import com.tuwaiq.bind.domain.models.PostData

data class PostDataDto(
    var postOwner:String = "",
    var postText:String = "",
    var postPhoto: String = "",
    var postTime:String ="",
    var postLat:String = "",
    var postLan:String = "",
    var userName:String = ""
){

    fun toPostData():PostData{
        return PostData(
            postOwner = postOwner,
            postText = postText,
            postPhoto = postPhoto,
            postTime = postTime,
            postLat = postLat,
            postLan = postLan,
            userName = userName
        )
    }

}
